package com.bolsa.banca_backend.dto;

import com.bolsa.banca_backend.utils.MovementType;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;



@Data
public class MovementResponse {
    private UUID id;
    private UUID accountId;
    private String accountNumber;

    private MovementType type;
    private BigDecimal amount;
    private BigDecimal balanceBefore;
    private BigDecimal balanceAfter;

    private LocalDate movementDate;
}
