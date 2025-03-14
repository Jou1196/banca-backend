package com.bolsa.banca_backend.service.impl;

import com.bolsa.banca_backend.dto.AccountReportDto;
import com.bolsa.banca_backend.entity.Account;
import com.bolsa.banca_backend.entity.Transaction;
import com.bolsa.banca_backend.repository.IAccountRepository;
import com.bolsa.banca_backend.repository.ITransactionRepository;
import com.bolsa.banca_backend.service.ITransactionService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
/**
 * Class TransactionServiceImpl
 */
public class TransactionServiceImpl implements ITransactionService {
    @Autowired
    private IAccountRepository accountRepository;

    @Autowired
    private ITransactionRepository transactionRepository;


    /**
     *
     * @param accountId
     * @param amount
     * @return
     */
    public String makeWithdrawal(Long accountId, Double amount) {

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));


        if (account.getBalance() < amount) {
            return "Saldo no disponible";
        }


        Double newBalance = account.getBalance() - amount;
        account.setBalance(newBalance);


        Transaction transaction = new Transaction();
        transaction.setTransactionType("Retiro");
        transaction.setAmount(amount);
        transaction.setInitialBalance(account.getBalance());
        transaction.setAvailableBalance(newBalance);
        transaction.setTransactionDate(LocalDateTime.now());


        transaction.setAccount(account);
        transactionRepository.save(transaction);
        accountRepository.save(account);

        return "Retiro realizado con éxito. Nuevo saldo: " + newBalance;
    }


    /**
     *
     * @param accountId
     * @param amount
     * @return
     */
    public Transaction makeDeposit(Long accountId, Double amount) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));
        Double initialBalance = account.getBalance();
        Double availableBalance = initialBalance + amount;
        account.setBalance(availableBalance);
        accountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setTransactionType("deposito");
        transaction.setAmount(amount);
        transaction.setInitialBalance(initialBalance);
        transaction.setAvailableBalance(availableBalance);
        transaction.setStatus("Completado");
        return transactionRepository.save(transaction);
    }

    /**
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public List<AccountReportDto> getEstadoDeCuenta(LocalDate startDate, LocalDate endDate) {
        List<Account> accounts = accountRepository.findAll();

        return accounts.stream().map(account -> {
            List<Transaction> transactions = account.getTransactions().stream()
                    .filter(tx -> !tx.getTransactionDate().toLocalDate().isBefore(startDate) &&
                            !tx.getTransactionDate().toLocalDate().isAfter(endDate))
                    .toList();

            AccountReportDto accountReportDto = new AccountReportDto(
                    account.getIdAccount(),
                    account.getAccountType(),
                    account.getBalance()
            );
            return accountReportDto;
        }).collect(Collectors.toList());
    }
}
