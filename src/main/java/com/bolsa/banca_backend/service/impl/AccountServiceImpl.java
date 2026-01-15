package com.bolsa.banca_backend.service.impl;

import com.bolsa.banca_backend.dto.AccountCreateRequest;
import com.bolsa.banca_backend.dto.AccountResponse;
import com.bolsa.banca_backend.entity.Account;
import com.bolsa.banca_backend.entity.Customer;
import com.bolsa.banca_backend.repository.IAccountRepository;
import com.bolsa.banca_backend.repository.ICustomerRepository;
import com.bolsa.banca_backend.service.IAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements IAccountService {

    private final IAccountRepository accountRepository;
    private final ICustomerRepository customerRepository;

    @Override
    public AccountResponse create(AccountCreateRequest req) {

        if (accountRepository.existsByAccountNumber(req.accountNumber())) {
            throw new IllegalArgumentException("Account number already exists");
        }

        Customer customer = customerRepository.findById(req.customerId())
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        Account a = new Account();
        a.setCustomer(customer);
        a.setAccountNumber(req.accountNumber());
        a.setAccountType(req.accountType());
        a.setInitialBalance(req.initialBalance());
        a.setActive(req.active());

        Account saved = accountRepository.save(a);
        return toResponse(saved);
    }

    @Override
    public List<AccountResponse> findAll() {
        return accountRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    public List<AccountResponse> findByCustomerId(UUID customerId) {
        return accountRepository.findByCustomerId(customerId).stream().map(this::toResponse).toList();
    }

    @Override
    public AccountResponse findById(UUID id) {
        Account a = accountRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        return toResponse(a);
    }

    @Override
    public void delete(UUID id) {
        if (!accountRepository.existsById(id)) {
            throw new IllegalArgumentException("Account not found");
        }
        accountRepository.deleteById(id);
    }

    private AccountResponse toResponse(Account a) {
        return new AccountResponse(
                a.getId(),
                a.getAccountNumber(),
                a.getAccountType(),
                a.getInitialBalance(),
                Boolean.TRUE.equals(a.getActive()),
                a.getCustomer().getId()
        );
    }
}

