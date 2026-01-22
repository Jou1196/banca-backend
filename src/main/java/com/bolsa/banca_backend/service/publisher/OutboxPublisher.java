package com.bolsa.banca_backend.service.publisher;

import com.bolsa.banca_backend.entity.OutboxEventEntity;
import com.bolsa.banca_backend.repository.IOutboxEventRepository;
import com.bolsa.banca_backend.utils.OutboxStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OutboxPublisher {

    private final IOutboxEventRepository repo;
    private final ObjectMapper objectMapper;

    public void publish(String aggregateType, UUID aggregateId, String eventType, Object payloadObj) {
        try {
            String payloadJson = objectMapper.writeValueAsString(payloadObj);

            OutboxEventEntity e = OutboxEventEntity.builder()
                    .id(UUID.randomUUID())
                    .aggregateType(aggregateType)
                    .aggregateId(aggregateId)
                    .eventType(eventType)
                    .payload(payloadJson)
                    .status(OutboxStatus.PENDING)
                    .attempts(0)
                    .availableAt(LocalDateTime.now())
                    .createdAt(LocalDateTime.now())
                    .build();

            repo.save(e);
        } catch (Exception ex) {
            throw new RuntimeException("Error serializando evento Outbox", ex);
        }
    }
}
