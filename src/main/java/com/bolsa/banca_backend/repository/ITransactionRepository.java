package com.bolsa.banca_backend.repository;

import com.bolsa.banca_backend.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface ITransactionRepository
 */
public interface ITransactionRepository extends JpaRepository<Transaction, Long> {
}
