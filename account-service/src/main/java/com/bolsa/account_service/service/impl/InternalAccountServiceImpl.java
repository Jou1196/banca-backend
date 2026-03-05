package com.bolsa.service.impl;

import com.bolsa.dto.MovementApplyRequest;
import com.bolsa.dto.MovementApplyResponse;
import com.bolsa.entity.Account;
import com.bolsa.repository.IAccountRepository;
import com.bolsa.service.InternalAccountService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InternalAccountServiceImpl implements InternalAccountService {

    private final IAccountRepository accountRepo;

    @Override
    @Transactional
    public MovementApplyResponse applyMovement(UUID accountId, MovementApplyRequest req) {

        Account a = accountRepo.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException("Cuenta no encontrada: " + accountId));

        BigDecimal before = a.getBalance();
        BigDecimal after;

        String type = req.getType() == null ? "" : req.getType().trim().toUpperCase();

        switch (type) {
            case "DEBIT" -> after = before.subtract(req.getAmount());
            case "CREDIT" -> after = before.add(req.getAmount());
            default -> throw new IllegalArgumentException("type debe ser DEBIT o CREDIT");
        }

        a.setBalance(after);
        accountRepo.save(a);

        MovementApplyResponse resp = new MovementApplyResponse();
        resp.setAccountId(a.getId());
        resp.setBalanceBefore(before);
        resp.setBalanceAfter(after);
        return resp;
    }
}