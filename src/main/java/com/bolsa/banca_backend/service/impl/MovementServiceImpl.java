package com.bolsa.banca_backend.service.impl;

import com.bolsa.banca_backend.dto.MovementCreateRequest;
import com.bolsa.banca_backend.dto.MovementResponse;
import com.bolsa.banca_backend.entity.Account;
import com.bolsa.banca_backend.entity.Movement;
import com.bolsa.banca_backend.excepciones.BusinessException;
import com.bolsa.banca_backend.repository.IAccountRepository;
import com.bolsa.banca_backend.repository.IMovementRepository;
import com.bolsa.banca_backend.service.IMovementService;
import com.bolsa.banca_backend.service.strategy.impl.MovementStrategyFactory;
import jakarta.persistence.EntityNotFoundException;
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
@Transactional
public class MovementServiceImpl implements IMovementService {

    private final IAccountRepository accountRepo;
    private final IMovementRepository movementRepo;
    private final MovementStrategyFactory strategyFactory;

    @Override
    public MovementResponse create(MovementCreateRequest req) {

        if (req.getAccountId() == null) {
            throw new BusinessException("El accountId es requerido");
        }

        if (req.getAmount() == null || req.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("El monto debe ser mayor a 0");
        }

        if (req.getType() == null) {
            throw new BusinessException("El tipo de movimiento es requerido");
        }

        Account account = accountRepo.findById(req.getAccountId())
                .orElseThrow(() ->
                        new EntityNotFoundException("Cuenta no encontrada: " + req.getAccountId())
                );

        if (!Boolean.TRUE.equals(account.getStatus())) {
            throw new BusinessException("La cuenta está inactiva");
        }

        BigDecimal balanceBefore = account.getBalance();

        BigDecimal balanceAfter = strategyFactory
                .getStrategy(req.getType())
                .apply(account, req.getAmount());

        account.setBalance(balanceAfter);
        accountRepo.save(account);

        Movement movement = Movement.builder()
                .account(account)
                .amount(req.getAmount())
                .type(req.getType())
                .balanceBefore(balanceBefore)
                .balanceAfter(balanceAfter)
                .movementDate(LocalDate.now())
                .createdAt(LocalDateTime.now())
                .build();

        return toMovementResponse(movementRepo.save(movement));
    }

    @Override
    @Transactional(readOnly = true)
    public List<MovementResponse> getByAccount(UUID accountId) {
        return movementRepo.findByAccountId(accountId)
                .stream()
                .map(this::toMovementResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<MovementResponse> getAll() {
        return movementRepo.findAll()
                .stream()
                .map(this::toMovementResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<MovementResponse> getByCustomerAndDates(UUID customerId, LocalDate from, LocalDate to) {
        return movementRepo.findReportMovements(customerId, from, to)
                .stream()
                .map(this::toMovementResponse)
                .toList();
    }

    private MovementResponse toMovementResponse(Movement m) {
        MovementResponse r = new MovementResponse();
        r.setId(m.getId());
        r.setAccountId(m.getAccount().getId());
        r.setAccountNumber(m.getAccount().getAccountNumber());
        r.setType(m.getType());
        r.setAmount(m.getAmount());
        r.setBalanceBefore(m.getBalanceBefore());
        r.setBalanceAfter(m.getBalanceAfter());
        r.setMovementDate(m.getMovementDate());
        return r;
    }
}
