package com.bolsa.banca_backend.service;

import com.bolsa.banca_backend.dto.ReportResponse;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Interface IReportService
 */
public interface IReportService {
    ReportResponse statusAccount(UUID customerId, LocalDate from, LocalDate to);
}
