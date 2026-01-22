package com.bolsa.banca_backend.service.publisher;

import com.bolsa.banca_backend.entity.OutboxEventEntity;
import com.bolsa.banca_backend.repository.IOutboxEventRepository;
import com.bolsa.banca_backend.utils.OutboxStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class OutboxProcessorJob {

    private final IOutboxEventRepository repo;
    private final OutboxHandlers handlers;

    @Scheduled(fixedDelay = 3000)
    public void run() {
        List<OutboxEventEntity> events = repo.findPending(LocalDateTime.now());

        for (OutboxEventEntity e : events) {
            int updated = repo.markProcessing(e.getId());
            if (updated == 0) continue;

            processOne(e.getId());
        }
    }

    @Transactional
    public void processOne(java.util.UUID id) {
        OutboxEventEntity e = repo.findById(id).orElse(null);
        if (e == null) return;

        try {
            handlers.handle(e);
            e.setStatus(OutboxStatus.PROCESSED);
            e.setProcessedAt(LocalDateTime.now());
            e.setLastError(null);
        } catch (Exception ex) {
            int attempts = e.getAttempts() + 1;
            e.setAttempts(attempts);
            e.setStatus(OutboxStatus.ERROR);
            e.setLastError(ex.getMessage());


            e.setAvailableAt(LocalDateTime.now().plusSeconds(10));
            e.setStatus(OutboxStatus.PENDING);

            log.error("Error procesando outbox {} type={} err={}", e.getId(), e.getEventType(), ex.getMessage(), ex);
        }
    }
}

