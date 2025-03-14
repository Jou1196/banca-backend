package com.bolsa.banca_backend.dto;


import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * Class CustomerDto
 */
public class CustomerDto {

    private Long idCustomer;

    private String fullName;
    private String address;
    private String phone;
    private String password;
    private String status;
}
