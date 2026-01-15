package com.bolsa.banca_backend.controller;



import com.bolsa.banca_backend.dto.MovementCreateRequest;
import com.bolsa.banca_backend.dto.MovementResponse;
import com.bolsa.banca_backend.service.IMovementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/movements")
@RequiredArgsConstructor
public class MovementController {

    private final IMovementService movementService;

    @GetMapping
    public ResponseEntity<List<MovementResponse>> findByAccount(@RequestParam UUID accountId) {
        return ResponseEntity.ok(movementService.findByAccount(accountId));
    }

    @PostMapping
    public ResponseEntity<MovementResponse> create(@RequestBody MovementCreateRequest req) {
        return ResponseEntity.ok(movementService.create(req));
    }

}

