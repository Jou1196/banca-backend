package com.bolsa.banca_backend.dto;

import com.bolsa.banca_backend.utils.MovementType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record MovementResponse(
        UUID id,
        UUID accountId,
        LocalDate movementDate,
        MovementType movementType,
        BigDecimal amount,
        BigDecimal availableBalance
) {}
