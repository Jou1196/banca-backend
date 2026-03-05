package com.bolsa.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class ReportResponse {

    private UUID customerId;
    private String customerName;
    private String identification;

    private LocalDate from;
    private LocalDate to;

    private List<AccountReportDto> accounts;

    public UUID getCustomerId() { return customerId; }
    public void setCustomerId(UUID customerId) { this.customerId = customerId; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getIdentification() { return identification; }
    public void setIdentification(String identification) { this.identification = identification; }

    public LocalDate getFrom() { return from; }
    public void setFrom(LocalDate from) { this.from = from; }

    public LocalDate getTo() { return to; }
    public void setTo(LocalDate to) { this.to = to; }

    public List<AccountReportDto> getAccounts() { return accounts; }
    public void setAccounts(List<AccountReportDto> accounts) { this.accounts = accounts; }
}