package com.kata.algorithms.bankaccountapp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kata.algorithms.bankaccountapp.commons.OperationType;
import com.kata.algorithms.bankaccountapp.entities.Account;
import com.kata.algorithms.bankaccountapp.entities.Statement;

@Repository
public interface StatementRepository extends JpaRepository<Statement, Long> {

	@Query("select s from Statement s where s.account = :account")
	List<Statement> findByAccount(@Param("account") Account account);

	@Query("select s from Statement s where s.account = :account and s.type= :operationType")
	List<Statement> findByAccountAndOperationType(@Param("account") Account account,
			@Param("operationType") OperationType operationType);

}
