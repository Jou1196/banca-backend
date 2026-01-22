package com.bolsa.banca_backend.service.impl;


import com.bolsa.banca_backend.dto.CustomerCreateRequest;
import com.bolsa.banca_backend.dto.CustomerDto;
import com.bolsa.banca_backend.dto.CustomerResponse;
import com.bolsa.banca_backend.dto.CustomerUpdateRequest;
import com.bolsa.banca_backend.entity.Customer;
import com.bolsa.banca_backend.repository.ICustomerRepository;
import com.bolsa.banca_backend.service.ICustomerService;
import jakarta.persistence.EntityNotFoundException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerServiceImpl implements ICustomerService {

    private final ICustomerRepository customerRepo;

    @Override
    public CustomerResponse create(CustomerCreateRequest req) {
        Customer customer = Customer.builder()
                .fullName(req.getFullName())
                .identification(req.getIdentification())
                .address(req.getAddress())
                .phone(req.getPhone())
                .password(req.getPassword())
                .status(req.getStatus() != null ? req.getStatus() : Boolean.TRUE)
                .build();

        Customer saved = customerRepo.save(customer);
        return toCustomerResponse(saved);
    }

    @Override
    public CustomerResponse update(UUID id, CustomerUpdateRequest req) {
        Customer customer = customerRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado: " + id));

        customer.setFullName(req.getFullName());
        customer.setIdentification(req.getIdentification());
        customer.setAddress(req.getAddress());
        customer.setPhone(req.getPhone());
        customer.setPassword(req.getPassword());
        customer.setStatus(req.getStatus());

        return toCustomerResponse(customerRepo.save(customer));
    }

    @Override
    @Transactional
    public CustomerDto getById(UUID id) {
        Customer customer = customerRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado: " + id));
        return toCustomerDto(customer);
    }

    @Override
    @Transactional
    public List<CustomerDto> getAll() {
        return customerRepo.findAll().stream().map(this::toCustomerDto).toList();
    }

    @Override
    public void delete(UUID id) {
        if (!customerRepo.existsById(id)) {
            throw new EntityNotFoundException("Cliente no encontrado: " + id);
        }
        customerRepo.deleteById(id);
    }


    private CustomerDto toCustomerDto(Customer c) {
        CustomerDto dto = new CustomerDto();
        dto.setId(c.getId());
        dto.setFullName(c.getFullName());
        dto.setIdentification(c.getIdentification());
        dto.setAddress(c.getAddress());
        dto.setPhone(c.getPhone());
        dto.setStatus(c.getStatus());
        return dto;
    }

    private CustomerResponse toCustomerResponse(Customer c) {
        CustomerResponse r = new CustomerResponse();
        r.setId(c.getId());
        r.setFullName(c.getFullName());
        r.setIdentification(c.getIdentification());
        r.setAddress(c.getAddress());
        r.setPhone(c.getPhone());
        r.setStatus(c.getStatus());
        return r;
    }
}

