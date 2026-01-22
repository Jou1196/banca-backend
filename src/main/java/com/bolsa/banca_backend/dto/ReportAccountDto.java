package com.bolsa.banca_backend.dto;
import com.bolsa.banca_backend.utils.AccountType;
import lombok.Data;

import java.math.BigDecimal;




@Data
public class ReportAccountDto {
    private String accountNumber;
    private AccountType type;
    private BigDecimal balance;
}