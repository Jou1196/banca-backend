package com.bolsa.banca_backend.service.strategy.impl;



import com.bolsa.banca_backend.entity.Account;
import com.bolsa.banca_backend.excepciones.BusinessException;
import com.bolsa.banca_backend.service.strategy.IMovementStrategy;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class WithdrawalStrategy implements IMovementStrategy {

    @Override
    public BigDecimal apply(Account account, BigDecimal amount) {

        if (account.getBalance().compareTo(amount) < 0) {
            throw new BusinessException("Saldo no disponible");
        }

        return account.getBalance().subtract(amount);
    }
}

