package com.bolsa.banca_backend.controller;


import com.bolsa.banca_backend.entity.Customer;
import com.bolsa.banca_backend.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

/**
 * Class CustomerController
 */
@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    ICustomerService customerService;


    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerService.createCustomer(customer);
    }


    /**
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Customer getCustomer(@PathVariable Long id) {
        return customerService.getCustomer(id);
    }

    /**
     *
     * @param id
     * @param customer
     * @return
     */
    @PutMapping("/{id}")
    public Customer updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
        return customerService.updateCustomer(id, customer);
    }

    /**
     *
     * @param id
     */

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
    }
}
