package com.bolsa.banca_backend.test;


import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerControllerIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testGetCustomerByIdSuccess() {

        Long existingCustomerId = 1L;


        ResponseEntity<String> response = restTemplate.getForEntity("/api/customers/{id}", String.class, existingCustomerId);



    }

    @Test
    public void testGetCustomerByIdNotFound() {

        Long nonExistingCustomerId = 999L;


        ResponseEntity<String> response = restTemplate.getForEntity("/api/customers/{id}", String.class, nonExistingCustomerId);



    }
}
