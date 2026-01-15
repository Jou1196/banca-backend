package com.bolsa.banca_backend.dto;
import java.math.BigDecimal;
import java.util.UUID;

public record ReportAccountDto(
        UUID accountId,
        String accountNumber,
        String accountType,
        BigDecimal currentBalance
) {}
