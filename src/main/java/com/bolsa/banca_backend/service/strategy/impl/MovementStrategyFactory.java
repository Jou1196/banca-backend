package com.bolsa.banca_backend.service.strategy.impl;


import com.bolsa.banca_backend.service.strategy.IMovementStrategy;
import com.bolsa.banca_backend.utils.MovementType;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;

@Component
public class MovementStrategyFactory {

    private final Map<MovementType, IMovementStrategy> strategies = new EnumMap<>(MovementType.class);

    public MovementStrategyFactory(
            DepositStrategy depositStrategy,
            WithdrawalStrategy withdrawalStrategy
    ) {
        strategies.put(MovementType.DEPOSIT, depositStrategy);
        strategies.put(MovementType.WITHDRAWAL, withdrawalStrategy);
    }

    public IMovementStrategy getStrategy(MovementType type) {
        return strategies.get(type);
    }
}

