package com.sample.banking.app.service.impl;


import com.sample.banking.app.config.BankUtils;
import com.sample.banking.app.exception.InvalidCustomerException;
import com.sample.banking.app.model.Account;
import com.sample.banking.app.model.Customer;
import com.sample.banking.app.model.User;
import com.sample.banking.app.repository.CustomerRepository;
import com.sample.banking.app.service.AccountService;
import com.sample.banking.app.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.sample.banking.app.config.BankConstant.ACCOUNT_STATUS_ACTIVE;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AccountService accountService;
    public CustomerServiceImpl(CustomerRepository repository) {
        this.customerRepository=repository;
    }

    private static final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Override
    public List<Customer> findAll() {
        Iterable<Customer> allCustomer = customerRepository.findAll();
        List<Customer> customerList = new ArrayList<>();
        allCustomer.forEach(customerList::add);
        return customerList;
    }

    /**
     * CREATE Customer
     *
     * @param user
     * @return
     */
    @Override
    @Transactional
    public ResponseEntity<Object> addCustomer(User user) {
        log.debug("Request to add customer");
        Customer customer = new Customer();
        customer.setFirstName(user.getFirstName());
        customer.setLastName(user.getLastName());
        customer.setEmail(user.getEmail());
        customer.setStatus(ACCOUNT_STATUS_ACTIVE);
        customer.setCreateDateTime(new Date());
        long customerNumber = BankUtils.generateNumber(customerRepository.getAllAccountNumber());
        customer.setCustomerNumber(customerNumber);
        Account account = accountService.createNewAccount(customer);
        customer.setAccount(account);
        customerRepository.save(customer);
        log.info("Customer Added Successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body("New Customer created successfully with Customer Number : " + customer
                .getCustomerNumber());
    }

    /**
     * READ Customer
     *
     * @param customerNumber
     * @return
     */

    @Override
    @Transactional
    public Customer findByCustomerNumber(Long customerNumber) throws InvalidCustomerException{
        log.info("Request to get Customer by customer number");
        return customerRepository.findByCustomerNumber(customerNumber)
                .orElseThrow(InvalidCustomerException::new);
    }

    @Override
    public Customer findByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

}
