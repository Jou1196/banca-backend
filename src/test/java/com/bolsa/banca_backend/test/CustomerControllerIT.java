package com.bolsa.banca_backend.test;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CustomerControllerIT extends IntegrationTestBase {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;

    @Test
    void customer_crud_flow_shouldWork() throws Exception {


        String createBody = """
        {
          "fullName": "Jose Lema",
          "identification": "0102030405",
          "address": "Otavalo sn y principal",
          "phone": "098254785",
          "password": "1234",
          "status": true
        }
        """;

        String createResponse = mockMvc.perform(post("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.fullName").value("Jose Lema"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        String customerId = objectMapper.readTree(createResponse).get("id").asText();


        mockMvc.perform(get("/api/clientes/{id}", customerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(customerId))
                .andExpect(jsonPath("$.fullName").value("Jose Lema"));


        String updateBody = """
        {
          "fullName": "Jose Lema Actualizado",
          "identification": "0102030405",
          "address": "Direccion nueva",
          "phone": "0999999999",
          "password": "abcd",
          "status": false
        }
        """;

        mockMvc.perform(put("/api/clientes/{id}", customerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(customerId))
                .andExpect(jsonPath("$.fullName").value("Jose Lema Actualizado"))
                .andExpect(jsonPath("$.status").value(false));


        mockMvc.perform(get("/api/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", isA(java.util.List.class)))
                .andExpect(jsonPath("$", not(empty())))
                .andExpect(jsonPath("$[0].id", notNullValue()));


        mockMvc.perform(delete("/api/clientes/{id}", customerId))
                .andExpect(status().isNoContent());


        mockMvc.perform(get("/api/clientes/{id}", customerId))
                .andExpect(status().is4xxClientError());
    }
}

