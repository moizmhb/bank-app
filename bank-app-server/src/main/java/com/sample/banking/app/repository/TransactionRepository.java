package com.sample.banking.app.repository;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

import com.sample.banking.app.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    public Optional<List<Transaction>> findAllByAccountNumber(Long accountNumber,Pageable pageable);

    public Optional<List<Transaction>> findByAccountNumber(Long accountNumber);


    
}
