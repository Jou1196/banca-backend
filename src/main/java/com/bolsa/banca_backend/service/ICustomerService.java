package com.bolsa.banca_backend.service;


import com.bolsa.banca_backend.dto.CustomerCreateRequest;
import com.bolsa.banca_backend.dto.CustomerResponse;
import com.bolsa.banca_backend.dto.CustomerUpdateRequest;


import java.util.List;
import java.util.UUID;

/**
 * Interface ICustomerService
 */
public interface ICustomerService {
    CustomerResponse create(CustomerCreateRequest req);
    List<CustomerResponse> findAll();
    CustomerResponse findById(UUID id);
    CustomerResponse update(UUID id, CustomerUpdateRequest req);
    void delete(UUID id);
}
