package com.bolsa.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class AccountReportDto {

    private UUID accountId;
    private String accountNumber;
    private String type;

    private BigDecimal balanceBefore;
    private BigDecimal balanceAfter;

    private List<MovementLineDto> movements;

    public UUID getAccountId() { return accountId; }
    public void setAccountId(UUID accountId) { this.accountId = accountId; }

    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public BigDecimal getBalanceBefore() { return balanceBefore; }
    public void setBalanceBefore(BigDecimal balanceBefore) { this.balanceBefore = balanceBefore; }

    public BigDecimal getBalanceAfter() { return balanceAfter; }
    public void setBalanceAfter(BigDecimal balanceAfter) { this.balanceAfter = balanceAfter; }

    public List<MovementLineDto> getMovements() { return movements; }
    public void setMovements(List<MovementLineDto> movements) { this.movements = movements; }
}