package com.bolsa.banca_backend.dto;



import com.bolsa.banca_backend.utils.AccountType;
import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;
import jakarta.validation.constraints.*;


@Data
public class AccountCreateRequest {

    @NotBlank(message = "accountNumber es requerido")
    @Size(max = 30)
    private String accountNumber;

    @NotNull(message = "type es requerido")
    private AccountType type;

    @NotNull(message = "initialBalance es requerido")
    @DecimalMin(value = "0.00", inclusive = true, message = "initialBalance no puede ser negativo")
    private BigDecimal initialBalance;

    private Boolean status;

    @NotNull(message = "customerId es requerido")
    private UUID customerId;
}



