package com.bolsa.banca_backend.service.impl;

import com.bolsa.banca_backend.dto.AccountCreateRequest;
import com.bolsa.banca_backend.dto.AccountDetailDto;
import com.bolsa.banca_backend.dto.AccountDto;
import com.bolsa.banca_backend.dto.AccountResponse;
import com.bolsa.banca_backend.entity.Account;
import com.bolsa.banca_backend.entity.Customer;

import com.bolsa.banca_backend.repository.IAccountRepository;
import com.bolsa.banca_backend.repository.ICustomerRepository;
import com.bolsa.banca_backend.service.IAccountService;
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
    private final ICustomerRepository customerRepo;

    @Override
    public AccountResponse create(AccountCreateRequest req) {
        Customer customer = customerRepo.findById(req.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado: " + req.getCustomerId()));

        BigDecimal initial = req.getInitialBalance() != null ? req.getInitialBalance() : BigDecimal.ZERO;

        Account account = Account.builder()
                .accountNumber(req.getAccountNumber())
                .type(req.getType())
                .initialBalance(initial)
                .balance(initial)
                .status(req.getStatus() != null ? req.getStatus() : Boolean.TRUE)
                .customer(customer)
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
        if (!accountRepo.existsById(id)) {
            throw new EntityNotFoundException("Cuenta no encontrada: " + id);
        }
        accountRepo.deleteById(id);
    }


    private AccountDto toAccountDto(Account a) {
        AccountDto dto = new AccountDto();
        dto.setId(a.getId());
        dto.setAccountNumber(a.getAccountNumber());
        dto.setType(a.getType());
        dto.setBalance(a.getBalance());
        dto.setStatus(a.getStatus());
        dto.setCustomerId(a.getCustomer().getId());
        return dto;
    }

    private AccountDetailDto toAccountDetailDto(Account a) {
        AccountDetailDto dto = new AccountDetailDto();
        dto.setId(a.getId());
        dto.setAccountNumber(a.getAccountNumber());
        dto.setType(a.getType());
        dto.setBalance(a.getBalance());
        dto.setStatus(a.getStatus());
        dto.setCustomerId(a.getCustomer().getId());
        dto.setCustomerFullName(a.getCustomer().getFullName());              // ✅ CORRECTO
        dto.setCustomerIdentification(a.getCustomer().getIdentification());  // ✅ CORRECTO
        return dto;
    }

    private AccountResponse toAccountResponse(Account a) {
        AccountResponse resp = new AccountResponse();
        resp.setId(a.getId());
        resp.setAccountNumber(a.getAccountNumber());
        resp.setType(a.getType());
        resp.setBalance(a.getBalance());
        resp.setStatus(a.getStatus());
        resp.setCustomerId(a.getCustomer().getId());
        return resp;
    }
}




