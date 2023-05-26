package com.sample.banking.app.service.impl;

import com.sample.banking.app.exception.InsufficientBalanceException;
import com.sample.banking.app.exception.InvalidAccountException;
import com.sample.banking.app.exception.InvalidAmountException;
import com.sample.banking.app.model.Account;
import com.sample.banking.app.model.Transaction;
import com.sample.banking.app.repository.AccountRepository;
import com.sample.banking.app.repository.CustomerRepository;
import com.sample.banking.app.repository.TransactionRepository;
import com.sample.banking.app.service.TransactionService;
import com.sample.banking.app.wrapper.DepositWithdrawal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.sample.banking.app.config.BankConstant.*;

@Service
public class TransactionServiceImpl implements TransactionService {

	@Autowired
    private CustomerRepository customerRepository;
	@Autowired
    private AccountRepository accountRepository;
	@Autowired
    private TransactionRepository transactionRepository;
	private static final Logger log = LoggerFactory.getLogger(TransactionServiceImpl.class);

    public TransactionServiceImpl(CustomerRepository repository) {
        this.customerRepository=repository;
    }

	/**
	 * Get all transactions for a specific account
	 *
	 * @param accountNumber
	 * @return
	 */
	public List<Transaction> findTransactionsByAccountNumber(Long accountNumber,int pageNumber, int pageSize) throws InvalidAccountException{
		log.debug("Request to find transactions by AccountNumber");
		Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("id").ascending());
		List<Transaction> transactionDetails = new ArrayList<>();
		Optional<Account> accountEntityOpt = accountRepository.findByAccountNumber(accountNumber);
		if(accountEntityOpt.isPresent()) {
			Optional<List<Transaction>> transactionEntitiesOpt = transactionRepository.findAllByAccountNumber(accountNumber,pageable);
			if(transactionEntitiesOpt.isPresent()) {
				transactionEntitiesOpt.get().forEach(transaction -> {
					transactionDetails.add(transaction);
				});
			}
			return transactionDetails;
		}else {
			log.info("Invalid Account Exception occurred");
			throw new InvalidAccountException();
		}
	}

	@Transactional
	@Override
	public ResponseEntity<Object> depositAmountByAccountNumber(DepositWithdrawal deposit) throws InvalidAccountException, InvalidAmountException{
		log.debug("Request for deposit for account number : {}",deposit.getAccountNumber() );
		if (!(deposit.getAmount() > MIN_AMOUNT  && deposit.getAmount() < MAX_AMOUNT)){
			throw new InvalidAmountException();
		}
		Optional<Account> byAccountNumber = accountRepository.findByAccountNumber(deposit.getAccountNumber());
		if(byAccountNumber.isPresent()){
			Account account = byAccountNumber.get();
			account.setAccountBalance(account.getAccountBalance() + deposit.getAmount());
			Account accountEntity = accountRepository.save(account);
			createTransactionHistory(deposit, DEPOSIT,accountEntity.getAccountBalance());
			return ResponseEntity.status(HttpStatus.OK).body("Account Updated Successfully");
		}else {
			log.info("Invalid Account Exception occurred");
			throw new InvalidAccountException();
		}
	}

	@Transactional
	@Override
	public ResponseEntity<Object> withDrawAmountByAccountNumber(DepositWithdrawal withDraw) throws InsufficientBalanceException, InvalidAccountException, InvalidAmountException {
		log.debug("Request for deposit for account number : {}",withDraw.getAccountNumber() );
		if (!(withDraw.getAmount() > MIN_AMOUNT  && withDraw.getAmount() < MAX_AMOUNT)){
			throw new InvalidAmountException();
		}
		Optional<Account> byAccountNumber = accountRepository.findByAccountNumber(withDraw.getAccountNumber());
		if(byAccountNumber.isPresent()){
			Account account = byAccountNumber.get();
			if(account.getAccountBalance() < 0 || (account.getAccountBalance() - withDraw.getAmount())<0){
				throw new InsufficientBalanceException();
			}
			account.setAccountBalance(account.getAccountBalance() - withDraw.getAmount());
			Account accountEntity = accountRepository.save(account);
			createTransactionHistory(withDraw, WITHDRAWAL,accountEntity.getAccountBalance());
			return ResponseEntity.status(HttpStatus.OK).body("Account Updated Successfully");
		}else {
			log.info("Invalid Account Exception occurred");
			throw new InvalidAccountException();
		}
	}

	private void createTransactionHistory(DepositWithdrawal depositWithDrawal, String transactionType, Long balance) {
		log.debug("Creating the Transaction History");
		Transaction transaction = new Transaction();
		transaction.setTransactionAmount(depositWithDrawal.getAmount());
		transaction.setTransactionType(transactionType);
		transaction.setAccountNumber(depositWithDrawal.getAccountNumber());
		transaction.setBalance(balance);
		transactionRepository.save(transaction);
	}

	@Override
	public Long getTransactionCount(){
		return transactionRepository.count();
	}


}
