package com.bolsa.strategy;

import com.bolsa.BusinessException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class MovementStrategyResolver {

    private final Map<String, MovementStrategy> strategiesByType;

    public MovementStrategyResolver(List<MovementStrategy> strategies) {
        this.strategiesByType = strategies.stream()
                .collect(Collectors.toMap(
                        s -> s.type().trim().toUpperCase(),
                        Function.identity()
                ));
    }

    public MovementStrategy resolve(String type) {
        if (type == null) {
            throw new BusinessException("El tipo de movimiento es requerido");
        }
        String key = type.trim().toUpperCase();
        MovementStrategy strategy = strategiesByType.get(key);
        if (strategy == null) {
            throw new BusinessException("Tipo de movimiento no soportado: " + key);
        }
        return strategy;
    }
}