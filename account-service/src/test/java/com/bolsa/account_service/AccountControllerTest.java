package com.bolsa.account_service;

import com.bolsa.controller.AccountController;
import com.bolsa.dto.AccountCreateRequest;
import com.bolsa.dto.AccountDetailDto;
import com.bolsa.dto.AccountDto;
import com.bolsa.dto.AccountResponse;
import com.bolsa.service.IAccountService;
import com.bolsa.utils.AccountType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class AccountControllerTest {

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private IAccountService accountService;

    @InjectMocks
    private AccountController accountController;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();
    }

    @Test
    void shouldCreateAccount() throws Exception {

        UUID accountId = UUID.randomUUID();

        AccountResponse response = new AccountResponse();
        response.setId(accountId);

        when(accountService.create(any(AccountCreateRequest.class))).thenReturn(response);

        AccountCreateRequest req = new AccountCreateRequest();
        req.setCustomerId(UUID.randomUUID());
        req.setAccountNumber("478758");
        req.setType(AccountType.AHORRO);
        req.setInitialBalance(new BigDecimal("100.00"));
        req.setStatus(true);

        mockMvc.perform(post("/api/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(accountId.toString()));

        verify(accountService, times(1)).create(any(AccountCreateRequest.class));
    }

    @Test
    void shouldGetAccountById() throws Exception {

        UUID id = UUID.randomUUID();

        AccountDetailDto detail = new AccountDetailDto();
        detail.setId(id);
        detail.setAccountNumber("478758");
        detail.setType(AccountType.AHORRO);
        detail.setBalance(new BigDecimal("99.50"));
        detail.setCustomerId(UUID.randomUUID());
        detail.setStatus(true);

        when(accountService.getById(id)).thenReturn(detail);

        mockMvc.perform(get("/api/accounts/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.accountNumber").value("478758"));

        verify(accountService, times(1)).getById(id);
    }

    @Test
    void shouldGetAccountsByCustomer() throws Exception {

        UUID customerId = UUID.randomUUID();

        AccountDto a1 = new AccountDto();
        a1.setId(UUID.randomUUID());
        a1.setAccountNumber("111");
        a1.setType(AccountType.AHORRO);
        a1.setBalance(new BigDecimal("10.00"));
        a1.setCustomerId(customerId);
        a1.setStatus(true);

        AccountDto a2 = new AccountDto();
        a2.setId(UUID.randomUUID());
        a2.setAccountNumber("222");
        a2.setType(AccountType.CORRIENTE);
        a2.setBalance(new BigDecimal("20.00"));
        a2.setCustomerId(customerId);
        a2.setStatus(true);

        when(accountService.getByCustomer(customerId)).thenReturn(List.of(a1, a2));

        mockMvc.perform(get("/api/accounts/por-cliente/{customerId}", customerId))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].customerId").value(customerId.toString()))
                .andExpect(jsonPath("$[1].customerId").value(customerId.toString()));

        verify(accountService, times(1)).getByCustomer(customerId);
    }

    @Test
    void shouldDeleteAccount() throws Exception {

        UUID id = UUID.randomUUID();

        doNothing().when(accountService).delete(id);

        mockMvc.perform(delete("/api/accounts/{id}", id))
                .andExpect(status().isNoContent());

        verify(accountService, times(1)).delete(id);
    }
}