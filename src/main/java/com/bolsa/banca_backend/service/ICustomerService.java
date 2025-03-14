package com.bolsa.banca_backend.service;


import com.bolsa.banca_backend.entity.Customer;

/**
 * Interface ICustomerService
 */
public interface ICustomerService {
    /**
     *
     * @param customer
     * @return
     */
    Customer createCustomer(Customer customer);

    /**
     *
     * @param id
     * @return
     */
    Customer getCustomer(Long id);

    /**
     *
     * @param id
     * @param customer
     * @return
     */
    Customer updateCustomer(Long id, Customer customer);

    /**
     *
     * @param id
     */
    void deleteCustomer(Long id);
}
