package com.bolsa.banca_backend.controller;




import com.bolsa.banca_backend.dto.AccountCreateRequest;
import com.bolsa.banca_backend.dto.AccountResponse;
import com.bolsa.banca_backend.service.IAccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final IAccountService service;

    @PostMapping
    public AccountResponse create(@RequestBody @Valid AccountCreateRequest req) {
        return service.create(req);
    }

    @GetMapping
    public List<AccountResponse> findAll(@RequestParam(value = "customerId", required = false) UUID customerId) {
        if (customerId != null) {
            return service.findByCustomerId(customerId);
        }
        return service.findAll();
    }

    @GetMapping("/{id}")
    public AccountResponse findById(@PathVariable UUID id) {
        return service.findById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}

