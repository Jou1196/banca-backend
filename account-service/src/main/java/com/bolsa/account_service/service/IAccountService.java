package com.bolsa.service;

import com.bolsa.dto.AccountCreateRequest;
import com.bolsa.dto.AccountDetailDto;
import com.bolsa.dto.AccountDto;
import com.bolsa.dto.AccountResponse;


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
