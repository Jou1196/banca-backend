package com.bolsa.banca_backend.repository;

import com.bolsa.banca_backend.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface IAccountRepository
 */
public interface IAccountRepository extends JpaRepository<Account, Long> {
}
