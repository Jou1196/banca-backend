package com.bolsa.banca_backend.dto;

import com.bolsa.banca_backend.utils.MovementType;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ReportMovementDto(
        LocalDate date,
        MovementType type,
        BigDecimal amount,
        BigDecimal availableBalance
) {}

