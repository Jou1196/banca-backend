package com.bolsa.client;

import com.bolsa.dto.MovementApplyRequest;
import com.bolsa.dto.MovementApplyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AccountClient {

    @Value("${account.service.url}")
    private String accountServiceUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public MovementApplyResponse apply(UUID accountId, MovementApplyRequest req) {
        String url = accountServiceUrl + "/internal/accounts/" + accountId + "/apply";
        return restTemplate.postForObject(url, req, MovementApplyResponse.class);
    }
}