package com.bolsa.banca_backend.controller;

import com.bolsa.banca_backend.dto.AccountReportDto;
import com.bolsa.banca_backend.dto.TransactionDto;
import com.bolsa.banca_backend.entity.Transaction;
import com.bolsa.banca_backend.service.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Class TransactionController
 */
@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    @Autowired
    private ITransactionService transactionService;

    /**
     *
     * @param request
     * @return
     */
    @PostMapping("/withdrawal")
    public ResponseEntity<String> makeWithdrawal(@RequestBody TransactionDto request) {
        String result = transactionService.makeWithdrawal(request.getAccountId(), request.getAmount());
        return ResponseEntity.ok(result);
    }

    /**
     *
     * @param accountId
     * @param amount
     * @return
     */
    @PostMapping("/deposit")
    public Transaction makeDeposit(@RequestParam Long accountId, @RequestParam Double amount) {
        return transactionService.makeDeposit(accountId, amount);
    }

    /**
     *
     * @param startDate
     * @param endDate
     * @return
     */
    @GetMapping("/estado-de-cuenta")
    public ResponseEntity<List<AccountReportDto>> getEstadoDeCuenta(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {

        List<AccountReportDto> report = transactionService.getEstadoDeCuenta(startDate, endDate);
        return ResponseEntity.ok(report);
    }
}
