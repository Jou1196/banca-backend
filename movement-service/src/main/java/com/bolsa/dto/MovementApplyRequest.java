package com.bolsa.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class MovementApplyRequest {

    @NotNull
    private String type;

    @NotNull
    private BigDecimal amount;
}