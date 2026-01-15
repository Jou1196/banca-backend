package com.bolsa.banca_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record AccountCreateRequest(
        @NotNull UUID customerId,
        @NotBlank String accountNumber,
        @NotBlank String accountType,
        @NotNull BigDecimal initialBalance,
        @NotNull Boolean active
) {}

