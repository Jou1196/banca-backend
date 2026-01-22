package com.bolsa.banca_backend.dto;



import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CustomerCreateRequest {

    @NotBlank(message = "fullName es requerido")
    @Size(max = 150, message = "fullName máximo 150")
    private String fullName;

    @NotBlank(message = "identification es requerido")
    @Size(max = 30, message = "identification máximo 30")
    private String identification;

    @NotBlank(message = "address es requerido")
    @Size(max = 200, message = "address máximo 200")
    private String address;

    @NotBlank(message = "phone es requerido")
    @Size(max = 30, message = "phone máximo 30")
    private String phone;

    @NotBlank(message = "password es requerido")
    @Size(min = 4, max = 100, message = "password entre 4 y 100")
    private String password;

    private Boolean status;
}



