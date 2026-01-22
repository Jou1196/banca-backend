package com.bolsa.banca_backend.test;

import com.bolsa.banca_backend.controller.CustomerController;
import com.bolsa.banca_backend.dto.CustomerCreateRequest;
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


        CustomerResponse response = new CustomerResponse();
        response.setId(id);
        response.setFullName("Jose Lema");
        response.setIdentification("0102030405");
        response.setAddress("Otavalo sn y principal");
        response.setPhone("098254785");
        response.setStatus(true);

        when(customerService.create(any(CustomerCreateRequest.class))).thenReturn(response);

        CustomerCreateRequest req = new CustomerCreateRequest();
        req.setFullName("Jose Lema");
        req.setIdentification("0102030405");
        req.setAddress("Otavalo sn y principal");
        req.setPhone("098254785");
        req.setPassword("1234");
        req.setStatus(true);

        mockMvc.perform(post("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.fullName").value("Jose Lema"))
                .andExpect(jsonPath("$.identification").value("0102030405"))
                .andExpect(jsonPath("$.status").value(true));
    }
}
