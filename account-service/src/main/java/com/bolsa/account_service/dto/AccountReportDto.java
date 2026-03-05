package com.bolsa.dto;


import com.bolsa.utils.AccountType;
import lombok.Data;

import java.math.BigDecimal;




@Data
public class AccountReportDto {
    private String accountNumber;
    private AccountType type;
    private BigDecimal balance;
}
