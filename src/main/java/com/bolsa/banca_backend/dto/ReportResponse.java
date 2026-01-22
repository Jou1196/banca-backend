package com.bolsa.banca_backend.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
public class ReportResponse {

    private UUID customerId;
    private String customerFullName;
    private String customerIdentification;

    private LocalDate from;
    private LocalDate to;

    private List<ReportAccountDto> cuentas;
    private List<ReportMovementDto> movimientos;
}

