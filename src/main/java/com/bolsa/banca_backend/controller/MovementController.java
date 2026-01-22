package com.bolsa.banca_backend.controller;



import com.bolsa.banca_backend.dto.MovementCreateRequest;
import com.bolsa.banca_backend.dto.MovementResponse;
import com.bolsa.banca_backend.service.IMovementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/movimientos")
@RequiredArgsConstructor
public class MovementController {

    private final IMovementService movementService;


    @PostMapping
    public ResponseEntity<MovementResponse> create(@RequestBody MovementCreateRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(movementService.create(req));
    }


    @GetMapping
    public ResponseEntity<List<MovementResponse>> getAll() {
        return ResponseEntity.ok(movementService.getAll());
    }


    @GetMapping("/por-cuenta/{accountId}")
    public ResponseEntity<List<MovementResponse>> getByAccount(@PathVariable UUID accountId) {
        return ResponseEntity.ok(movementService.getByAccount(accountId));
    }


    @GetMapping("/por-cliente")
    public ResponseEntity<List<MovementResponse>> getByCustomerAndDates(
            @RequestParam UUID customerId,
            @RequestParam LocalDate from,
            @RequestParam LocalDate to
    ) {
        return ResponseEntity.ok(movementService.getByCustomerAndDates(customerId, from, to));
    }
}

