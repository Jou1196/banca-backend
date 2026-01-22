package com.bolsa.banca_backend.dto;

import lombok.Data;

@Data
public class CustomerUpdateRequest {
    private String fullName;
    private String identification;
    private String address;
    private String phone;
    private String password;
    private Boolean status;
}


