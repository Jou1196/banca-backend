package com.bolsa.banca_backend.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record MovementCreatedEvent(
        UUID movementId,
        UUID accountId,
        String type,
        BigDecimal amount,
        BigDecimal balanceAfter,
        LocalDateTime createdAt
) {}

