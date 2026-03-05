package com.bolsa.controller;

import com.bolsa.entity.Movement;
import com.bolsa.service.IMovementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/movements")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class MovementController {

    private final IMovementService movementService;


    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<Movement>> getByAccountAndDates(
            @PathVariable UUID accountId,
            @RequestParam LocalDate from,
            @RequestParam LocalDate to
    ) {
        return ResponseEntity.ok(movementService.getByAccountAndDates(accountId, from, to));
    }


    @GetMapping("/account/{accountId}/all")
    public ResponseEntity<List<Movement>> getByAccount(@PathVariable UUID accountId) {
        return ResponseEntity.ok(movementService.getByAccountId(accountId));
    }
}