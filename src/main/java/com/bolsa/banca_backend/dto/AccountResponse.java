package com.bolsa.banca_backend.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record AccountResponse(
        UUID id,
        String accountNumber,
        String accountType,
        BigDecimal initialBalance,
        boolean active,
        UUID customerId
) {}

