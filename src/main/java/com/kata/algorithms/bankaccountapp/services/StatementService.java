package com.kata.algorithms.bankaccountapp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kata.algorithms.bankaccountapp.commons.OperationType;
import com.kata.algorithms.bankaccountapp.entities.Account;
import com.kata.algorithms.bankaccountapp.entities.Statement;
import com.kata.algorithms.bankaccountapp.repositories.StatementRepository;

@Component
public class StatementService {

	@Autowired
	StatementRepository statementRepository;

	public Statement createStatement(Statement statement) {
		return statementRepository.save(statement);
	}

	public List<Statement> findAllStatement() {
		return statementRepository.findAll();
	}

	public Optional<Statement> findStatementById(Long id) {
		return statementRepository.findById(id);
	}

	public List<Statement> findStatementsByAccount(Account account) {
		return statementRepository.findByAccount(account);
	}

	public List<Statement> findByAccountAndOperationType(Account account, OperationType type) {
		return statementRepository.findByAccountAndOperationType(account, type);
	}
}
