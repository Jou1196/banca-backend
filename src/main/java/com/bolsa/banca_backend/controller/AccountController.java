package com.bolsa.banca_backend.controller;


import com.bolsa.banca_backend.dto.AccountDto;
import com.bolsa.banca_backend.entity.Account;
import com.bolsa.banca_backend.service.impl.AccountServiceImpl;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Class AccountController
 */
@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    @Autowired
    private AccountServiceImpl accountService;

    /**
     *
     * @param accountDto
     * @return
     */
    @PostMapping
    public ResponseEntity<String> createAccount( @NotNull @RequestBody AccountDto accountDto) {
        accountService.createAccount(accountDto);
        return ResponseEntity.ok("Cuenta creada con éxito");
    }

    /**
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Account getAccount( @NotBlank  @PathVariable Long id) {
        return accountService.getAccount(id);
    }

    /**
     *
     * @param id
     * @param account
     * @return
     */
    @PutMapping("/{id}")
    public Account updateAccount( @NotBlank @PathVariable Long id, @NotBlank  @RequestBody Account account) {
        return accountService.updateAccount(id, account);
    }

    /**
     *
     * @param id
     */
    @DeleteMapping("/{id}")
    public void deleteAccount(@NotBlank @PathVariable Long id) {
        accountService.deleteAccount(id);
    }
}
