package com.bolsa.banca_backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;


@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "full_name", nullable = false, length = 150)
    private String fullName;

    @Column(name = "identification", nullable = false, unique = true, length = 30)
    private String identification;

    @Column(name = "address", nullable = false, length = 200)
    private String address;

    @Column(name = "phone", nullable = false, length = 30)
    private String phone;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Column(name = "status", nullable = false)
    private Boolean status;
}





