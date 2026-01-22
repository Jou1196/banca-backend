package com.bolsa.banca_backend.service.impl;


import com.bolsa.banca_backend.dto.ReportAccountDto;
import com.bolsa.banca_backend.dto.ReportMovementDto;
import com.bolsa.banca_backend.dto.ReportResponse;
import com.bolsa.banca_backend.entity.Account;
import com.bolsa.banca_backend.entity.Customer;
import com.bolsa.banca_backend.entity.Movement;

import com.bolsa.banca_backend.repository.IAccountRepository;
import com.bolsa.banca_backend.repository.ICustomerRepository;
import com.bolsa.banca_backend.repository.IMovementRepository;
import com.bolsa.banca_backend.service.IReportService;
import com.bolsa.banca_backend.utils.MovementType;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReportServiceImpl implements IReportService {

    private final ICustomerRepository customerRepo;
    private final IAccountRepository accountRepo;
    private final IMovementRepository movementRepo;

    @Override
    public ReportResponse statusAccount(UUID customerId, LocalDate from, LocalDate to) {
        Customer customer = customerRepo.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado: " + customerId));

        List<Account> accounts = accountRepo.findByCustomerId(customerId);
        List<Movement> movements = movementRepo.findReportMovements(customerId, from, to);

        List<ReportAccountDto> cuentas = accounts.stream().map(a -> {
            ReportAccountDto dto = new ReportAccountDto();
            dto.setAccountNumber(a.getAccountNumber());
            dto.setType(a.getType());
            dto.setBalance(a.getBalance());
            return dto;
        }).toList();

        List<ReportMovementDto> movs = movements.stream().map(m -> {
            ReportMovementDto dto = new ReportMovementDto();
            dto.setFecha(m.getMovementDate());
            dto.setCliente(customer.getFullName());
            dto.setNumeroCuenta(m.getAccount().getAccountNumber());
            dto.setTipo(m.getAccount().getType());
            dto.setSaldoInicial(m.getBalanceBefore());
            dto.setEstado(m.getAccount().getStatus());


            BigDecimal signed = (m.getType() == MovementType.DEPOSIT)
                    ? m.getAmount()
                    : m.getAmount().negate();
            dto.setMovimiento(signed);

            dto.setSaldoDisponible(m.getBalanceAfter());
            return dto;
        }).toList();

        ReportResponse resp = new ReportResponse();
        resp.setCustomerId(customer.getId());
        resp.setCustomerFullName(customer.getFullName());
        resp.setCustomerIdentification(customer.getIdentification());
        resp.setFrom(from);
        resp.setTo(to);
        resp.setCuentas(cuentas);
        resp.setMovimientos(movs);
        return resp;
    }
}
