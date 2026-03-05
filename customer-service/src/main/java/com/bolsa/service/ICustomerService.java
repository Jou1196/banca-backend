package com.bolsa.service;





import com.bolsa.dto.CustomerCreateRequest;
import com.bolsa.dto.CustomerDto;
import com.bolsa.dto.CustomerResponse;
import com.bolsa.dto.CustomerUpdateRequest;

import java.util.List;
import java.util.UUID;

/**
 * Interface ICustomerService
 */
public interface ICustomerService {
    CustomerResponse create(CustomerCreateRequest req);
    CustomerResponse update(UUID id, CustomerUpdateRequest req);
    CustomerDto getById(UUID id);
    List<CustomerDto> getAll();
    void delete(UUID id);
}
