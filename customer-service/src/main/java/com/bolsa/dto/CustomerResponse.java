package com.bolsa.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class CustomerResponse {
    private UUID id;
    private String fullName;
    private String identification;
    private String address;
    private String phone;
    private Boolean status;
}

