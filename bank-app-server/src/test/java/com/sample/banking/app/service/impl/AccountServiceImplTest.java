package com.sample.banking.app.service.impl;

import com.sample.banking.app.model.Account;
import com.sample.banking.app.model.Customer;
import com.sample.banking.app.repository.AccountRepository;
import com.sample.banking.app.service.AccountService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static com.sample.banking.app.config.BankConstant.ACCOUNT_STATUS_ACTIVE;
import static com.sample.banking.app.config.BankConstant.TYPE_SAVING;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountService accountService;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateNewAccount() {
        when(accountRepository.save(any())).thenReturn(createAccountResponse());
        Account account = accountRepository.save(createAccountResponse());
        verify(accountRepository).save(any());
        Assertions.assertEquals(12345L, account.getAccountNumber());
        assertEquals(ACCOUNT_STATUS_ACTIVE, account.getAccountStatus());
        assertEquals(TYPE_SAVING, account.getAccountType());
    }

    private Customer createCustomerResponse() {
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setCustomerNumber(12345L);
        return customer;
    }

    private Account createAccountResponse() {
        Account account = new Account();
        account.setAccountType(TYPE_SAVING);
        account.setAccountStatus(ACCOUNT_STATUS_ACTIVE);
        account.setAccountNumber(12345L);
        return account;
    }




}