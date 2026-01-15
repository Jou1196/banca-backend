package com.bolsa.banca_backend.repository;

import com.bolsa.banca_backend.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface IAccountRepository extends JpaRepository<Account, UUID> {
    List<Account> findByCustomerId(UUID customerId);
    boolean existsByAccountNumber(String accountNumber);

}

