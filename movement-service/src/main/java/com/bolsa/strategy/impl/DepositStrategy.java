package com.bolsa.strategy.impl;

import com.bolsa.client.AccountClient;
import com.bolsa.dto.MovementApplyResponse;
import com.bolsa.strategy.MovementStrategy;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class DepositStrategy implements MovementStrategy {

    @Override
    public String type() {
        return "DEPOSIT";
    }

    @Override
    public MovementApplyResponse apply(AccountClient accountClient, UUID accountId, BigDecimal amount) {
        validateAmount(amount);
        return accountClient.apply(accountId, buildApplyRequest(type(), amount));
    }
}