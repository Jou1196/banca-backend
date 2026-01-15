package com.bolsa.banca_backend.service.impl;

import com.bolsa.banca_backend.dto.MovementCreateRequest;
import com.bolsa.banca_backend.dto.MovementResponse;
import com.bolsa.banca_backend.entity.Account;
import com.bolsa.banca_backend.entity.Movement;
import com.bolsa.banca_backend.repository.IAccountRepository;
import com.bolsa.banca_backend.repository.IMovementRepository;
import com.bolsa.banca_backend.service.IMovementService;
import com.bolsa.banca_backend.utils.MovementType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MovementServiceImpl implements IMovementService {

    private static final BigDecimal DAILY_DEBIT_LIMIT = new BigDecimal("1000.00");

    private final IMovementRepository movementRepository;
    private final IAccountRepository accountRepository;

    @Override
    public MovementResponse create(MovementCreateRequest req) {
        validateRequest(req);

        Account account = accountRepository.findById(req.accountId())
                .orElseThrow(() -> new IllegalArgumentException("Cuenta no encontrada"));


        BigDecimal currentBalance = getCurrentBalance(account.getId(), account.getInitialBalance());

        BigDecimal normalizedAmount = normalizeAmount(req.movementType(), req.amount());


        if (req.movementType() == MovementType.DEBIT) {

            if (currentBalance.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Insuficientes fondos");
            }


            BigDecimal todayDebits = movementRepository.sumDailyAbsByType(account.getId(), MovementType.DEBIT, req.movementDate());
            BigDecimal newDebitAbs = normalizedAmount.abs(); // debit is negative, abs is positive

            if (todayDebits.add(newDebitAbs).compareTo(DAILY_DEBIT_LIMIT) > 0) {
                throw new IllegalArgumentException("Limite excedido");
            }


            BigDecimal after = currentBalance.add(normalizedAmount);
            if (after.compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("Insuficientes fondos");
            }
        }

        BigDecimal availableBalance = currentBalance.add(normalizedAmount);

        Movement m = new Movement();
        m.setAccount(account);
        m.setMovementDate(req.movementDate());
        m.setMovementType(req.movementType());
        m.setAmount(normalizedAmount);
        m.setAvailableBalance(availableBalance);

        Movement saved = movementRepository.save(m);
        return toResponse(saved);
    }

    @Override
    public List<MovementResponse> findByAccount(UUID accountId) {
        return movementRepository.findByAccountIdOrderByMovementDateDesc(accountId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private void validateRequest(MovementCreateRequest req) {
        if (req == null) throw new IllegalArgumentException("Movimiento requerido");
        if (req.accountId() == null) throw new IllegalArgumentException("Cuenta requerida");
        if (req.movementDate() == null) throw new IllegalArgumentException("Fecha de movimiento requerida");
        if (req.movementType() == null) throw new IllegalArgumentException("Tipo de movimiento requerido");
        if (req.amount() == null) throw new IllegalArgumentException("Cantidad requerida");
        if (req.amount().compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("La cantidad debe ser  > 0");
    }

    private BigDecimal normalizeAmount(MovementType type, BigDecimal amount) {

        if (type == MovementType.DEBIT) return amount.abs().negate();
        return amount.abs();
    }

    private BigDecimal getCurrentBalance(UUID accountId, BigDecimal initialBalance) {
        List<Movement> list = movementRepository.findByAccountIdOrderByMovementDateDesc(accountId);
        if (list.isEmpty()) return initialBalance == null ? BigDecimal.ZERO : initialBalance;
        return list.get(0).getAvailableBalance();
    }

    private MovementResponse toResponse(Movement m) {
        return new MovementResponse(
                m.getId(),
                m.getAccount().getId(),
                m.getMovementDate(),
                m.getMovementType(),
                m.getAmount(),
                m.getAvailableBalance()
        );
    }
}
