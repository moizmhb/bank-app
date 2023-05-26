package com.sample.banking.app.service;

import com.sample.banking.app.model.Account;
import com.sample.banking.app.model.Customer;
import org.springframework.http.ResponseEntity;

public interface AccountService {

    public ResponseEntity<Object> findByAccountNumber(Long accountNumber);
    
    public Account createNewAccount(Customer customer);


}
