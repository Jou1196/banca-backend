package com.bolsa.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "customer-service", url = "${services.customer.url}")
public interface CustomerClient {

    @GetMapping("/api/customers/{id}")
    CustomerDto getById(@PathVariable UUID id);

    class CustomerDto {
        public UUID id;
        public String fullName;
        public String identification;
        public Boolean status;
    }
}