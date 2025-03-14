package com.bolsa.banca_backend.service;

import com.bolsa.banca_backend.dto.AccountDto;
import com.bolsa.banca_backend.entity.Account;

/**
 * Interface IAccountService
 */
public interface IAccountService {
    /**
     *
     * @param accountDto
     * @return
     */
    public Account createAccount(AccountDto accountDto);

    /**
     *
     * @param id
     * @return
     */
    public Account getAccount(Long id);

    /**
     *
     * @param id
     * @param account
     * @return
     */
    public Account updateAccount(Long id, Account account);

    /**
     *
     * @param id
     */
    public void deleteAccount(Long id);
}
