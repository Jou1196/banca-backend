package com.bolsa.banca_backend.controller;


import com.bolsa.banca_backend.entity.Customer;
import com.bolsa.banca_backend.service.ICustomerService;
import jakarta.validation.constraints.NotBlank;
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
    public Customer createCustomer( @NotBlank  @RequestBody Customer customer) {
        return customerService.createCustomer(customer);
    }


    /**
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Customer getCustomer( @NotBlank @PathVariable Long id) {
        return customerService.getCustomer(id);
    }

    /**
     *
     * @param id
     * @param customer
     * @return
     */
    @PutMapping("/{id}")
    public Customer updateCustomer(@NotBlank @PathVariable Long id,@NotBlank  @RequestBody Customer customer) {
        return customerService.updateCustomer(id, customer);
    }

    /**
     *
     * @param id
     */

    @DeleteMapping("/{id}")
    public void deleteCustomer(@NotBlank @PathVariable Long id) {
        customerService.deleteCustomer(id);
    }
}
