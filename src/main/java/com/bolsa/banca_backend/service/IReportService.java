package com.bolsa.banca_backend.service;

import com.bolsa.banca_backend.dto.ReportResponse;

import java.time.LocalDate;
import java.util.UUID;

public interface IReportService {
    ReportResponse getCustomerReport(UUID customerId, LocalDate from, LocalDate to);
}
