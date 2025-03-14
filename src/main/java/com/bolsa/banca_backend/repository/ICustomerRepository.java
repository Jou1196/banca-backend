package com.bolsa.banca_backend.repository;

import com.bolsa.banca_backend.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface ICustomerRepository
 */
public interface ICustomerRepository extends JpaRepository<Customer, Long> {
}
