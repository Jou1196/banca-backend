package com.bolsa.controller;

import com.bolsa.dto.MovementApplyRequest;
import com.bolsa.dto.MovementApplyResponse;
import com.bolsa.service.InternalAccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/internal/accounts")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class InternalAccountController {

    private final InternalAccountService internalAccountService;

    @PostMapping("/{id}/apply")
    public MovementApplyResponse applyMovement(
            @PathVariable UUID id,
            @RequestBody @Valid MovementApplyRequest req
    ) {
        return internalAccountService.applyMovement(id, req);
    }
}