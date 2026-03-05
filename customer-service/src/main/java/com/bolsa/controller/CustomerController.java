package com.bolsa.controller;




import com.bolsa.dto.CustomerCreateRequest;
import com.bolsa.dto.CustomerDto;
import com.bolsa.dto.CustomerResponse;
import com.bolsa.dto.CustomerUpdateRequest;
import com.bolsa.service.ICustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/customers")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class CustomerController {

    private final ICustomerService customerService;


    @PostMapping
    public ResponseEntity<CustomerResponse> create(@Valid @RequestBody CustomerCreateRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.create(req));
    }


    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> update(@PathVariable UUID id,
                                                   @RequestBody CustomerUpdateRequest req) {
        return ResponseEntity.ok(customerService.update(id, req));
    }


    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(customerService.getById(id));
    }


    @GetMapping
    public ResponseEntity<List<CustomerDto>> getAll() {
        return ResponseEntity.ok(customerService.getAll());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        customerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

