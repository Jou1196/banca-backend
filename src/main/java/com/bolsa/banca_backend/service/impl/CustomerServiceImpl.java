package com.bolsa.banca_backend.service.impl;


import com.bolsa.banca_backend.entity.Customer;
import com.bolsa.banca_backend.repository.ICustomerRepository;
import com.bolsa.banca_backend.service.ICustomerService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Class CustomerServiceImpl
 */
@Service
@AllArgsConstructor
public class CustomerServiceImpl implements ICustomerService {

    @Autowired
    ICustomerRepository customerRepository;


    /**
     *
     * @param customer
     * @return
     */
    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    /**
     *
     * @param id
     * @return
     */
    @Override

    public Customer getCustomer(Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
    }

    /**
     *
     * @param id
     * @param customer
     * @return
     */
    @Override

    public Customer updateCustomer(Long id, Customer customer) {
        Customer existingCustomer = getCustomer(id);
        existingCustomer.setFullName(customer.getFullName());
        existingCustomer.setAddress(customer.getAddress());
        existingCustomer.setPhone(customer.getPhone());
        existingCustomer.setPassword(customer.getPassword());
        existingCustomer.setStatus(customer.getStatus());
        return customerRepository.save(existingCustomer);
    }

    /**
     *
     * @param id
     */
    @Override

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }


}
