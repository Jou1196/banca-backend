package com.bolsa.service.impl;

import com.bolsa.client.AccountClient;
import com.bolsa.client.CustomerClient;
import com.bolsa.client.MovementClient;
import com.bolsa.dto.AccountReportDto;
import com.bolsa.dto.MovementLineDto;
import com.bolsa.dto.ReportResponse;
import com.bolsa.service.IReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements IReportService {

    private final CustomerClient customerClient;
    private final AccountClient accountClient;
    private final MovementClient movementClient;

    @Override
    public ReportResponse statusAccount(UUID customerId, LocalDate from, LocalDate to) {

        // 1) Cliente
        CustomerClient.CustomerDto customer = customerClient.getById(customerId);

        // 2) Cuentas del cliente
        List<AccountClient.AccountDto> accounts = accountClient.getByCustomer(customerId);

        // 3) Armar reporte
        ReportResponse resp = new ReportResponse();
        resp.setCustomerId(customerId);
        resp.setCustomerName(customer.fullName);
        resp.setIdentification(customer.identification);
        resp.setFrom(from);
        resp.setTo(to);

        List<AccountReportDto> accountReports = new ArrayList<>();

        for (AccountClient.AccountDto acc : accounts) {

            // 3.1 Movimientos por cuenta y rango
            List<MovementClient.MovementDto> movs =
                    movementClient.getByAccountAndDates(acc.id, from.toString(), to.toString());

            // ordenar por fecha
            movs.sort(Comparator.comparing(m -> m.date));

            // mapear movimientos
            List<MovementLineDto> lines = movs.stream().map(m -> {
                MovementLineDto line = new MovementLineDto();
                line.setMovementId(m.id);
                line.setDate(m.date);
                line.setType(m.type);
                line.setAmount(m.amount);
                line.setBalanceBefore(m.balanceBefore);
                line.setBalanceAfter(m.balanceAfter);
                return line;
            }).toList();

            // balanceBefore/balanceAfter del rango (si no hay movimientos, usamos balance actual)
            BigDecimal balanceBefore = lines.isEmpty()
                    ? (acc.balance != null ? acc.balance : BigDecimal.ZERO)
                    : lines.get(0).getBalanceBefore();

            BigDecimal balanceAfter = lines.isEmpty()
                    ? (acc.balance != null ? acc.balance : BigDecimal.ZERO)
                    : lines.get(lines.size() - 1).getBalanceAfter();

            AccountReportDto accReport = new AccountReportDto();
            accReport.setAccountId(acc.id);
            accReport.setAccountNumber(acc.accountNumber);
            accReport.setType(acc.type);
            accReport.setBalanceBefore(balanceBefore);
            accReport.setBalanceAfter(balanceAfter);
            accReport.setMovements(lines);

            accountReports.add(accReport);
        }

        resp.setAccounts(accountReports);
        return resp;
    }
}