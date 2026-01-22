package com.bolsa.banca_backend.test;

import com.bolsa.banca_backend.controller.MovementController;
import com.bolsa.banca_backend.dto.MovementResponse;
import com.bolsa.banca_backend.excepciones.GlobalExceptionHandler;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
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

                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void shouldCreateMovementSuccessfully() throws Exception {
        UUID movementId = UUID.randomUUID();
        UUID accountId = UUID.randomUUID();

        MovementResponse response = new MovementResponse();
        response.setId(movementId);
        response.setAccountId(accountId);
        response.setAccountNumber("1234567890");
        response.setType(MovementType.DEPOSIT);
        response.setAmount(new BigDecimal("100.00"));
        response.setBalanceBefore(new BigDecimal("1000.00"));
        response.setBalanceAfter(new BigDecimal("1100.00"));
        response.setMovementDate(LocalDate.parse("2022-02-10"));

        when(movementService.create(any())).thenReturn(response);

        String body = """
        {
          "accountId": "%s",
          "amount": 100.00,
          "type": "DEPOSIT"
        }
        """.formatted(accountId);

        mockMvc.perform(post("/api/movimientos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(movementId.toString()))
                .andExpect(jsonPath("$.accountId").value(accountId.toString()))
                .andExpect(jsonPath("$.accountNumber").value("1234567890"))
                .andExpect(jsonPath("$.type").value("DEPOSIT"))
                // BigDecimal en JSON puede venir como 1100 o 1100.00, por eso usamos número:
                .andExpect(jsonPath("$.balanceAfter").value(1100.00));

        verify(movementService).create(any());
    }

    @Test
    void shouldReturnErrorWhenInsufficientFunds() throws Exception {
        when(movementService.create(any()))
                .thenThrow(new IllegalArgumentException("Saldo no disponible"));

        String body = """
        {
          "accountId": "11111111-1111-1111-1111-111111111111",
          "amount": 500.00,
          "type": "WITHDRAWAL"
        }
        """;

        mockMvc.perform(post("/api/movimientos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Saldo no disponible"));

        verify(movementService).create(any());
    }
}
