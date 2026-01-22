package com.bolsa.banca_backend.dto;

import com.bolsa.banca_backend.utils.AccountType;


import java.math.BigDecimal;
import java.time.LocalDate;


import lombok.Data;

@Data
public class ReportMovementDto {

    private LocalDate fecha;
    private String cliente;
    private String numeroCuenta;
    private AccountType tipo;
    private BigDecimal saldoInicial;
    private Boolean estado;
    private BigDecimal movimiento;
    private BigDecimal saldoDisponible;
}

