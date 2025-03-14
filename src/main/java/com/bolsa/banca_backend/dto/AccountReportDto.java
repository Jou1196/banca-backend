package com.bolsa.banca_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;



@Data
@AllArgsConstructor
@Getter
@Setter
/**
 * Class AccountReportDto
 */
public class AccountReportDto {
    private Long idAccount;
    private String accountType;
    private Double balance;

}
