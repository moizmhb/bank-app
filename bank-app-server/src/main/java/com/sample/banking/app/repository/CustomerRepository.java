package com.sample.banking.app.repository;

import java.util.List;
import java.util.Optional;

import com.sample.banking.app.model.Customer;
import lombok.extern.java.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    public Optional<Customer> findByCustomerNumber(Long customerNumber);

    @Query(value = "select account_number from account",nativeQuery = true)
    public List<Long> getAllAccountNumber();

/*    @Query("SELECT c FROM Customer c where c.email = :email")*/
    public Customer findByEmail(String email);


    
}