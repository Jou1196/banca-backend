package com.bolsa.service.impl;

import com.bolsa.dto.*;
import com.bolsa.entity.Account;
import com.bolsa.repository.IAccountRepository;
import com.bolsa.service.IAccountService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountServiceImpl implements IAccountService {

    private final IAccountRepository accountRepo;

    @Override
    public AccountResponse create(AccountCreateRequest req) {

        // ✅ NO buscar Customer aquí (eso vive en customer-service)
        BigDecimal initial = req.getInitialBalance() != null ? req.getInitialBalance() : BigDecimal.ZERO;

        Account account = Account.builder()
                .accountNumber(req.getAccountNumber())
                .type(req.getType())
                .initialBalance(initial)
                .balance(initial)
                .status(req.getStatus() != null ? req.getStatus() : Boolean.TRUE)
                .customerId(req.getCustomerId())   // ✅ solo ID
                .build();

        return toAccountResponse(accountRepo.save(account));
    }

    @Override
    @Transactional(readOnly = true)
    public AccountDetailDto getById(UUID id) {
        Account a = accountRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cuenta no encontrada: " + id));
        return toAccountDetailDto(a);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountDto> getAll() {
        return accountRepo.findAll().stream().map(this::toAccountDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountDto> getByCustomer(UUID customerId) {
        return accountRepo.findByCustomerId(customerId).stream().map(this::toAccountDto).toList();
    }

    @Override
    public void delete(UUID id) {
        if (!accountRepo.existsById(id)) throw new EntityNotFoundException("Cuenta no encontrada: " + id);
        accountRepo.deleteById(id);
    }

    private AccountDto toAccountDto(Account a) {
        AccountDto dto = new AccountDto();
        dto.setId(a.getId());
        dto.setAccountNumber(a.getAccountNumber());
        dto.setType(a.getType());
        dto.setBalance(a.getBalance());
        dto.setStatus(a.getStatus());
        dto.setCustomerId(a.getCustomerId()); // ✅
        return dto;
    }

    private AccountDetailDto toAccountDetailDto(Account a) {
        AccountDetailDto dto = new AccountDetailDto();
        dto.setId(a.getId());
        dto.setAccountNumber(a.getAccountNumber());
        dto.setType(a.getType());
        dto.setBalance(a.getBalance());
        dto.setStatus(a.getStatus());
        dto.setCustomerId(a.getCustomerId()); // ✅

        // ❌ estas 2 líneas NO pueden estar aquí (son datos de customer-service)
        // dto.setCustomerFullName(...)
        // dto.setCustomerIdentification(...)

        return dto;
    }

    private AccountResponse toAccountResponse(Account a) {
        AccountResponse resp = new AccountResponse();
        resp.setId(a.getId());
        resp.setAccountNumber(a.getAccountNumber());
        resp.setType(a.getType());
        resp.setBalance(a.getBalance());
        resp.setStatus(a.getStatus());
        resp.setCustomerId(a.getCustomerId()); // ✅
        return resp;
    }
}