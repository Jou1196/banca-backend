package com.bolsa.banca_backend.service.impl;

import com.bolsa.banca_backend.dto.CustomerCreateRequest;
import com.bolsa.banca_backend.dto.CustomerResponse;
import com.bolsa.banca_backend.dto.CustomerUpdateRequest;
import com.bolsa.banca_backend.entity.Customer;
import com.bolsa.banca_backend.repository.ICustomerRepository;
import com.bolsa.banca_backend.service.ICustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements ICustomerService {

    private final ICustomerRepository repository;

    @Override
    public CustomerResponse create(CustomerCreateRequest req) {


        if (repository.existsByCustomerCode(req.customerCode())) {
            throw new IllegalArgumentException("Customer ya existe");
        }

        Customer c = new Customer();
        c.setCustomerCode(req.customerCode());
        c.setPassword(req.password());
        c.setActive(req.active());

        c.setName(req.name());
        c.setGender(req.gender());
        c.setAge(req.age());
        c.setIdentification(req.identification());
        c.setAddress(req.address());
        c.setPhone(req.phone());

        Customer saved = repository.save(c);
        return toResponse(saved);
    }

    @Override
    public List<CustomerResponse> findAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    public CustomerResponse findById(UUID id) {
        Customer c = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer no encontrado"));
        return toResponse(c);
    }

    @Override
    public CustomerResponse update(UUID id, CustomerUpdateRequest req) {
        Customer c = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer no encontrado"));

        c.setPassword(req.password());
        c.setActive(req.active());

        c.setName(req.name());
        c.setGender(req.gender());
        c.setAge(req.age());
        c.setIdentification(req.identification());
        c.setAddress(req.address());
        c.setPhone(req.phone());

        return toResponse(repository.save(c));
    }

    @Override
    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Customer no encontrado");
        }
        repository.deleteById(id);
    }

    private CustomerResponse toResponse(Customer c) {
        return new CustomerResponse(
                c.getId(),
                c.getCustomerCode(),
                Boolean.TRUE.equals(c.getActive()),
                c.getName(),
                c.getGender(),
                c.getAge(),
                c.getIdentification(),
                c.getAddress(),
                c.getPhone()
        );
    }
}
