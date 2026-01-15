package com.bolsa.banca_backend.test;

import com.bolsa.banca_backend.controller.CustomerController;
import com.bolsa.banca_backend.dto.CustomerResponse;
import com.bolsa.banca_backend.service.ICustomerService;
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

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private ICustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    void shouldCreateCustomer() throws Exception {

        UUID id = UUID.randomUUID();

        CustomerResponse response = new CustomerResponse(
                id,
                "john.doe",
                true,
                "John Doe",
                "Male",
                30,
                "0102030405",
                "Main Street",
                "0999999999"
        );

        when(customerService.create(any())).thenReturn(response);

        String body = """
        {
          "customerCode": "john.doe",
          "password": "1234",
          "active": true,
          "name": "John Doe",
          "gender": "Male",
          "age": 30,
          "identification": "0102030405",
          "address": "Main Street",
          "phone": "0999999999"
        }
        """;

        mockMvc.perform(post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.customerCode").value("john.doe"))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.active").value(true));
    }
}
