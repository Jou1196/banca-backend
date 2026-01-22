package com.bolsa.banca_backend.repository;


import com.bolsa.banca_backend.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ICustomerRepository extends JpaRepository<Customer, UUID> {
}
