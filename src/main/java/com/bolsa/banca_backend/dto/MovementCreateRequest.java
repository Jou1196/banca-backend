package com.bolsa.banca_backend.dto;

import com.bolsa.banca_backend.utils.MovementType;
import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;



import jakarta.validation.constraints.*;


@Data
public class MovementCreateRequest {

    @NotNull(message = "accountId es requerido")
    private UUID accountId;

    @NotNull(message = "amount es requerido")
    @DecimalMin(value = "0.01", message = "amount debe ser mayor a 0")
    private BigDecimal amount;

    @NotNull(message = "type es requerido")
    private MovementType type;
}

