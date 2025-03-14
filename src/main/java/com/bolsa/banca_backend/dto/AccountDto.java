package com.bolsa.banca_backend.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
/**
 * Class AccountDto
 */
public class AccountDto {
    private String accountType;
    private Double balance;
    private Long customerId;
}
