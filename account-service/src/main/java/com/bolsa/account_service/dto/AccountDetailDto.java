package com.bolsa.dto;


import com.bolsa.utils.AccountType;
import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;

@Data
public class AccountDetailDto {
    private UUID id;
    private String accountNumber;
    private AccountType type;
    private BigDecimal balance;
    private Boolean status;

    private UUID customerId;
    private String customerFullName;
    private String customerIdentification;
}