package com.bolsa.banca_backend.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class CustomerDto {
    private UUID id;
    private String fullName;
    private String identification;
    private String address;
    private String phone;
    private Boolean status;
}

