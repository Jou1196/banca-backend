package com.bolsa.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class MovementApplyResponse {
    private UUID accountId;
    private BigDecimal balanceBefore;
    private BigDecimal balanceAfter;
}