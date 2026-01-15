package com.bolsa.banca_backend.test;

import com.bolsa.banca_backend.controller.MovementController;
import com.bolsa.banca_backend.dto.MovementResponse;
import com.bolsa.banca_backend.service.IMovementService;
import com.bolsa.banca_backend.utils.MovementType;
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
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class MovementControllerTest {

    private MockMvc mockMvc;

    @Mock
    private IMovementService movementService;

    @InjectMocks
    private MovementController movementController;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(movementController)
                .build();
    }

    @Test
    void shouldCreateMovementSuccessfully() throws Exception {
        UUID movementId = UUID.randomUUID();
        UUID accountId = UUID.randomUUID();

        MovementResponse response = new MovementResponse(
                movementId,
                accountId,
                LocalDate.parse("2022-02-10"),
                MovementType.CREDIT,
                new BigDecimal("100.00"),
                new BigDecimal("1100.00")
        );

        when(movementService.create(any())).thenReturn(response);

        String body = """
        {
          "accountId": "%s",
          "movementDate": "2022-02-10",
          "movementType": "CREDIT",
          "amount": 100
        }
        """.formatted(accountId);

        mockMvc.perform(post("/movements")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(movementId.toString()))
                .andExpect(jsonPath("$.movementType").value("CREDIT"))
                .andExpect(jsonPath("$.availableBalance").value(1100.00));
    }

    @Test
    void shouldThrowWhenInsufficientFunds() {
        when(movementService.create(any()))
                .thenThrow(new IllegalArgumentException("Insufficient funds"));

        String body = """
        {
          "accountId": "11111111-1111-1111-1111-111111111111",
          "movementDate": "2022-02-10",
          "movementType": "DEBIT",
          "amount": 500
        }
        """;

        Exception ex = assertThrows(Exception.class, () ->
                mockMvc.perform(post("/movements")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(body))
                        .andReturn()
        );

        // Spring wraps it as ServletException, the cause contains the real exception
        Throwable root = (ex.getCause() != null) ? ex.getCause() : ex;
        assertTrue(root instanceof IllegalArgumentException);
        assertEquals("Insufficient funds", root.getMessage());
    }

    @Test
    void shouldThrowWhenDailyLimitExceeded() {
        when(movementService.create(any()))
                .thenThrow(new IllegalArgumentException("Daily limit exceeded"));

        String body = """
        {
          "accountId": "11111111-1111-1111-1111-111111111111",
          "movementDate": "2022-02-10",
          "movementType": "DEBIT",
          "amount": 900
        }
        """;

        Exception ex = assertThrows(Exception.class, () ->
                mockMvc.perform(post("/movements")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(body))
                        .andReturn()
        );

        Throwable root = (ex.getCause() != null) ? ex.getCause() : ex;
        assertTrue(root instanceof IllegalArgumentException);
        assertEquals("Daily limit exceeded", root.getMessage());
    }
}
