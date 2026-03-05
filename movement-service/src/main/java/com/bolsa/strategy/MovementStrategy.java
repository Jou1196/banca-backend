package com.bolsa.strategy;

import com.bolsa.client.AccountClient;
import com.bolsa.dto.MovementApplyRequest;
import com.bolsa.dto.MovementApplyResponse;

import java.math.BigDecimal;
import java.util.UUID;

public interface MovementStrategy {
    String type();

    MovementApplyResponse apply(AccountClient accountClient, UUID accountId, BigDecimal amount);

    default String normalize(String type) {
        return type == null ? null : type.trim().toUpperCase();
    }

    default void validateAmount(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor a 0");
        }
    }

    default MovementApplyRequest buildApplyRequest(String type, BigDecimal amount) {
        MovementApplyRequest req = new MovementApplyRequest();
        req.setType(type);
        req.setAmount(amount);
        return req;
    }
}