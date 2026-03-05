package com.bolsa.controller;




import com.bolsa.dto.AccountCreateRequest;
import com.bolsa.dto.AccountDetailDto;
import com.bolsa.dto.AccountDto;
import com.bolsa.dto.AccountResponse;
import com.bolsa.service.IAccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/accounts")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AccountController {

    private final IAccountService accountService;


    @PostMapping
    public ResponseEntity<AccountResponse> create(@Valid @RequestBody AccountCreateRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(accountService.create(req));
    }


    @GetMapping("/{id}")
    public ResponseEntity<AccountDetailDto> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(accountService.getById(id));
    }


    @GetMapping
    public ResponseEntity<List<AccountDto>> getAll() {
        return ResponseEntity.ok(accountService.getAll());
    }


    @GetMapping("/por-cliente/{customerId}")
    public ResponseEntity<List<AccountDto>> getByCustomer(@PathVariable UUID customerId) {
        return ResponseEntity.ok(accountService.getByCustomer(customerId));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        accountService.delete(id);
        return ResponseEntity.noContent().build();
    }


}


