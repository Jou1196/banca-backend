package com.bolsa.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@FeignClient(name = "movement-service", url = "${services.movement.url}")
public interface MovementClient {

    @GetMapping("/api/movements/account/{accountId}")
    List<MovementDto> getByAccountAndDates(
            @PathVariable UUID accountId,
            @RequestParam String from,
            @RequestParam String to
    );

    class MovementDto {
        public UUID id;
        public UUID accountId;
        public BigDecimal amount;
        public String type;
        public LocalDateTime date;
        public BigDecimal balanceBefore;
        public BigDecimal balanceAfter;
    }
}