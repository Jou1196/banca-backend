package com.bolsa.banca_backend.service.strategy;



import com.bolsa.banca_backend.entity.Account;

import java.math.BigDecimal;

public interface IMovementStrategy {

    BigDecimal apply(Account account, BigDecimal amount);
}

