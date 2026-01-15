package com.bolsa.banca_backend.dto;

import com.bolsa.banca_backend.utils.MovementType;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record MovementCreateRequest(
        @NotNull UUID accountId,
        @NotNull LocalDate movementDate,
        @NotNull MovementType movementType,
        @NotNull BigDecimal amount
) {}
