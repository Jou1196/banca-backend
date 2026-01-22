package com.bolsa.banca_backend.service.publisher;

import com.bolsa.banca_backend.dto.MovementCreatedEvent;
import com.bolsa.banca_backend.entity.OutboxEventEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OutboxHandlers {

    private final ObjectMapper mapper;

    public void handle(OutboxEventEntity e) throws Exception {
        switch (e.getEventType()) {
            case "MovementCreated" -> handleMovementCreated(e.getPayload());
            default -> throw new IllegalArgumentException("No handler for eventType=" + e.getEventType());
        }
    }

    private void handleMovementCreated(String payload) throws Exception {
        MovementCreatedEvent ev = mapper.readValue(payload, MovementCreatedEvent.class);



        System.out.println("ASYNC: movement created -> " + ev.movementId()
                + " account=" + ev.accountId()
                + " type=" + ev.type()
                + " amount=" + ev.amount());
    }
}

