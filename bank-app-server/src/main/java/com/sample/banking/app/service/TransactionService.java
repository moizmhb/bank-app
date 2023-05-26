package com.sample.banking.app.service;

import com.sample.banking.app.model.Transaction;
import com.sample.banking.app.wrapper.DepositWithdrawal;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TransactionService {
    public List<Transaction> findTransactionsByAccountNumber(Long accountNumber, int pageNumber, int pageSize);

    public ResponseEntity<Object> depositAmountByAccountNumber(DepositWithdrawal deposit);

    public ResponseEntity<Object> withDrawAmountByAccountNumber(DepositWithdrawal withDraw);

    Long getTransactionCount();
}
