package com.bolsa.banca_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CustomerCreateRequest(
        @NotBlank String customerCode,
        @NotBlank String password,
        @NotNull Boolean active,

        @NotBlank String name,
        String gender,
        Integer age,
        String identification,
        String address,
        String phone
) {}

