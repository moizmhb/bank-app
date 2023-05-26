package com.sample.banking.app.service.impl;

import com.sample.banking.app.exception.InvalidCustomerException;
import com.sample.banking.app.model.Account;
import com.sample.banking.app.model.Customer;
import com.sample.banking.app.model.User;
import com.sample.banking.app.repository.CustomerRepository;
import com.sample.banking.app.service.AccountService;
import com.sample.banking.app.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private AccountService accountService;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testFindAll_ReturnsAllCustomers() {
        // Arrange
        List<Customer> expectedCustomers = new ArrayList<>();
        when(customerRepository.findAll()).thenReturn(expectedCustomers);
        List<Customer> actualCustomers = customerService.findAll();
        assertEquals(expectedCustomers, actualCustomers);
    }

    @Test
    void testFindByCustomerNumber_ExistingCustomer_ReturnsCustomer() throws InvalidCustomerException {
        // Arrange
        long customerNumber = 123456789;
        Customer customer = new Customer();
        customer.setCustomerNumber(customerNumber);
        when(customerRepository.findByCustomerNumber(customerNumber)).thenReturn(Optional.of(customer));
        Customer result = customerService.findByCustomerNumber(customerNumber);
        assertEquals(customer, result);
    }

    @Test
    void testFindByCustomerNumber_NonExistingCustomer_ThrowsInvalidCustomerException() {
        long customerNumber = 987654321;
        when(customerRepository.findByCustomerNumber(customerNumber)).thenReturn(Optional.empty());
        assertThrows(InvalidCustomerException.class, () -> customerService.findByCustomerNumber(customerNumber));
    }
}