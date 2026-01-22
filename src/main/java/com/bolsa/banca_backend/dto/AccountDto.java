package com.bolsa.banca_backend.dto;



import com.bolsa.banca_backend.utils.AccountType;
import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;

@Data
public class AccountDto {
    private UUID id;
    private String accountNumber;
    private AccountType type;
    private BigDecimal balance;
    private Boolean status;
    private UUID customerId;
}
