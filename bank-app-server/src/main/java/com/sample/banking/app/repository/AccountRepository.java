package com.sample.banking.app.repository;

import java.util.List;
import java.util.Optional;

import com.sample.banking.app.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

	Optional<Account> findByAccountNumber(Long accountNumber);
	@Query(value = "select account_number from account",nativeQuery = true)
	public List<Long> getAllAccountNumber();

}
