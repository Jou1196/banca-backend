package com.bolsa.service.impl;

import com.bolsa.BusinessException;
import com.bolsa.client.AccountClient;
import com.bolsa.dto.MovementApplyResponse;
import com.bolsa.dto.MovementCreateRequest;
import com.bolsa.entity.Movement;
import com.bolsa.repository.MovementRepository;
import com.bolsa.service.IMovementService;
import com.bolsa.strategy.MovementStrategy;
import com.bolsa.strategy.MovementStrategyResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MovementServiceImpl implements IMovementService {

    private final MovementRepository movementRepo;
    private final AccountClient accountClient;
    private final MovementStrategyResolver strategyResolver;

    @Override
    @Transactional
    public Movement create(MovementCreateRequest req) {

        if (req.getAccountId() == null) {
            throw new BusinessException("El accountId es requerido");
        }

        if (req.getAmount() == null || req.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("El monto debe ser mayor a 0");
        }

        if (req.getType() == null) {
            throw new BusinessException("El tipo de movimiento es requerido");
        }

        MovementStrategy strategy = strategyResolver.resolve(req.getType());
        String normalizedType = req.getType().trim().toUpperCase();

        MovementApplyResponse applyResp =
                strategy.apply(accountClient, req.getAccountId(), req.getAmount());

        Movement movement = Movement.builder()
                .accountId(req.getAccountId())
                .type(normalizedType)
                .amount(req.getAmount())
                .date(LocalDateTime.now())
                .balanceBefore(applyResp.getBalanceBefore())
                .balanceAfter(applyResp.getBalanceAfter())
                .build();

        return movementRepo.save(movement);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Movement> getByAccountAndDates(UUID accountId, LocalDate from, LocalDate to) {
        LocalDateTime start = from.atStartOfDay();
        LocalDateTime end = to.plusDays(1).atStartOfDay();
        return movementRepo.findByAccountIdAndDateBetween(accountId, start, end);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Movement> getByAccountId(UUID accountId) {
        return movementRepo.findByAccountId(accountId);
    }
}