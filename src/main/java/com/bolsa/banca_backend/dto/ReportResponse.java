package com.bolsa.banca_backend.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record ReportResponse(
        UUID customerId,
        String customerName,
        LocalDate from,
        LocalDate to,
        List<ReportAccountDto> accounts,
        BigDecimal totalCredit,
        BigDecimal totalDebit,
        String pdfBase64
) {}

