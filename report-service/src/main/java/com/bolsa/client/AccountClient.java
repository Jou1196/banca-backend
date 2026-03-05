package com.bolsa.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@FeignClient(name="accountClient", url="${services.account.url}")
public interface AccountClient {

    @GetMapping("/api/accounts/por-cliente/{customerId}")
    List<AccountDto> getByCustomer(@PathVariable UUID customerId);

    class AccountDto {
        public UUID id;
        public String accountNumber;
        public String type;
        public BigDecimal balance;
        public Boolean status;
        public UUID customerId;
    }
}