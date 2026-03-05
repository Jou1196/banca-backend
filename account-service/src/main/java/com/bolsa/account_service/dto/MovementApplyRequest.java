package com.bolsa.dto;
import lombok.Data;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
@Data
public class MovementApplyRequest {
    @NotNull
    private String type;
    @NotNull
    private BigDecimal amount;


}