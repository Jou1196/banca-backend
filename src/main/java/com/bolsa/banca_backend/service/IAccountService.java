package com.bolsa.banca_backend.service;

import com.bolsa.banca_backend.dto.AccountCreateRequest;
import com.bolsa.banca_backend.dto.AccountResponse;


import java.util.List;
import java.util.UUID;

/**
 * Interface IAccountService
 */
public interface IAccountService {
    AccountResponse create(AccountCreateRequest req);
    List<AccountResponse> findAll();
    List<AccountResponse> findByCustomerId(UUID customerId);
    AccountResponse findById(UUID id);
    void delete(UUID id);
}
