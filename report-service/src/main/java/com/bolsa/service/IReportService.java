package com.bolsa.service;

import com.bolsa.dto.ReportResponse;

import java.time.LocalDate;
import java.util.UUID;

public interface IReportService {
    ReportResponse statusAccount(UUID customerId, LocalDate from, LocalDate to);
}