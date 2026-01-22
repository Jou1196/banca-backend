package com.bolsa.banca_backend.service;

import com.bolsa.banca_backend.dto.AccountCreateRequest;
import com.bolsa.banca_backend.dto.AccountDetailDto;
import com.bolsa.banca_backend.dto.AccountDto;
import com.bolsa.banca_backend.dto.AccountResponse;


import java.util.List;
import java.util.UUID;

/**
 * Interface IAccountService
 */
public interface IAccountService {
    AccountResponse create(AccountCreateRequest req);
    AccountDetailDto getById(UUID id);
    List<AccountDto> getAll();
    List<AccountDto> getByCustomer(UUID customerId);
    void delete(UUID id);
}
