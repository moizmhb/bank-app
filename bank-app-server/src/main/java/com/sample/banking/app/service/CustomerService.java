package com.sample.banking.app.service;

import com.sample.banking.app.model.Customer;
import com.sample.banking.app.model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CustomerService {
    List<Customer> findAll();

    ResponseEntity<Object> addCustomer(User user);

    Customer findByCustomerNumber(Long customerNumber);
    
    Customer findByEmail(String email);
}
