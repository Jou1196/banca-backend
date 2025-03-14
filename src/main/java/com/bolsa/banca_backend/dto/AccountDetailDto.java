package com.bolsa.banca_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@Getter
@Setter
/**
 * Class AccountDetailDto
 */
public class AccountDetailDto {
    private Long customerId;
    private String fullName;
    private String address;
    private String phone;

    private Long accountId;
    private String accountType;
    private Double balance;

    private Long transactionId;
    private String transactionType;
    private Double amount;
    private Double initialBalance;
    private Double availableBalance;
    private LocalDateTime transactionDate;
}
