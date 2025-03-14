package com.bolsa.banca_backend.service;

import com.bolsa.banca_backend.dto.AccountReportDto;
import com.bolsa.banca_backend.entity.Transaction;

import java.time.LocalDate;
import java.util.List;

/**
 *  Interface ITransactionService
 */
public interface ITransactionService {
    /**
     *
     * @param accountId
     * @param amount
     * @return
     */
    public String makeWithdrawal(Long accountId, Double amount);

    /**
     *
     * @param accountId
     * @param amount
     * @return
     */
    public Transaction makeDeposit(Long accountId, Double amount);

    /**
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public List<AccountReportDto> getEstadoDeCuenta(LocalDate startDate, LocalDate endDate);
}
