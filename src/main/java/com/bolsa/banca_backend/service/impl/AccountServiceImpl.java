package com.bolsa.banca_backend.service.impl;



import com.bolsa.banca_backend.dto.AccountDto;
import com.bolsa.banca_backend.entity.Account;
import com.bolsa.banca_backend.entity.Customer;
import com.bolsa.banca_backend.repository.IAccountRepository;
import com.bolsa.banca_backend.repository.ICustomerRepository;
import com.bolsa.banca_backend.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Class AccountServiceImpl
 */
@Service
public class AccountServiceImpl implements IAccountService {

    @Autowired
    private IAccountRepository accountRepository;

    @Autowired
    private ICustomerRepository customerRepository;

    /**
     *
     * @param accountDto
     * @return
     */
    @Override

    public Account createAccount(AccountDto accountDto) {

        Customer customer = customerRepository.findById(accountDto.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));


        Account accountSave = new Account();
        accountSave.setAccountType(accountDto.getAccountType());
        accountSave.setBalance(accountDto.getBalance());
        accountSave.setCustomer(customer);


        return accountRepository.save(accountSave);
    }

    /**
     *
     * @param id
     * @return
     */
    @Override

    public Account getAccount(Long id) {
        return accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));
    }

    /**
     *
     * @param id
     * @param account
     * @return
     */
    @Override

    public Account updateAccount(Long id, Account account) {
        Account existingAccount = getAccount(id);
        existingAccount.setAccountType(account.getAccountType());
        existingAccount.setBalance(account.getBalance());
        return accountRepository.save(existingAccount);
    }

    /**
     *
     * @param id
     */
    @Override

    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }



}
