package com.bolsa.banca_backend.dto;

import lombok.*;



@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * Class TransactionDto
 */
public class TransactionDto {

    private Long accountId;
    private Double amount;

}
