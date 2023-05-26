package com.sample.banking.app.service.impl;

import com.sample.banking.app.config.BankUtils;
import com.sample.banking.app.exception.InvalidAccountException;
import com.sample.banking.app.model.Account;
import com.sample.banking.app.model.Customer;
import com.sample.banking.app.repository.AccountRepository;
import com.sample.banking.app.repository.CustomerRepository;
import com.sample.banking.app.repository.TransactionRepository;
import com.sample.banking.app.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.sample.banking.app.config.BankConstant.ACCOUNT_STATUS_ACTIVE;
import static com.sample.banking.app.config.BankConstant.TYPE_SAVING;


@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
    private CustomerRepository customerRepository;
	@Autowired
    private AccountRepository accountRepository;
	@Autowired
    private TransactionRepository transactionRepository;

	private static final Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);
    public AccountServiceImpl(CustomerRepository repository) {
        this.customerRepository=repository;
    }

	/**
	 * Find Account
	 *
	 * @param accountNumber
	 * @return
	 */
	public ResponseEntity<Object> findByAccountNumber(Long accountNumber) throws InvalidAccountException {
		log.debug("Fetching account details by account number :{}",accountNumber);
		Optional<Account> accountOptionalObject = accountRepository.findByAccountNumber(accountNumber);

		if(accountOptionalObject.isPresent()) {
			return ResponseEntity.status(HttpStatus.FOUND).body(accountOptionalObject.get());
		} else {
			log.info("Account Details not found for account number :{}",accountNumber);
			throw new InvalidAccountException();
		}
	}

	/**
	 * Create new account
	 *
	 * @param customer
	 *
	 * @return
	 */
	@Override
	@Transactional
	public Account createNewAccount(Customer customer) {
		Account account = createAccountObject();
		return accountRepository.save(account);
	}

	private Account createAccountObject() {
		List<Long> allAccountNumber = accountRepository.getAllAccountNumber();
		long newAccountNumber = BankUtils.generateNumber(allAccountNumber);
		Account account = new Account();
		account.setAccountStatus(ACCOUNT_STATUS_ACTIVE);
		account.setAccountNumber(newAccountNumber);
		account.setAccountType(TYPE_SAVING);
		account.setAccountBalance(0L);
		return account;
	}



}
