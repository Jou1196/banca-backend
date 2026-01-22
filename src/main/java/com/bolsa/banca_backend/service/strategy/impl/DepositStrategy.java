package com.bolsa.banca_backend.service.strategy.impl;


import com.bolsa.banca_backend.entity.Account;
import com.bolsa.banca_backend.service.strategy.IMovementStrategy;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DepositStrategy implements IMovementStrategy {

    @Override
    public BigDecimal apply(Account account, BigDecimal amount) {
        return account.getBalance().add(amount);
    }
}

