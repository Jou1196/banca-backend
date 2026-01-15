package com.bolsa.banca_backend.dto;

import java.util.UUID;

public record CustomerResponse(
        UUID id,
        String customerCode,
        boolean active,
        String name,
        String gender,
        Integer age,
        String identification,
        String address,
        String phone
) {}

