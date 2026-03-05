package com.bolsa.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class MovementCreateRequest {

    @NotNull
    private UUID accountId;

    @NotNull
    private String type;

    @NotNull
    private BigDecimal amount;

}