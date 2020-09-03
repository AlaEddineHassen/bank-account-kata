package com.kata.algorithms.bankaccountapp.controllers;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kata.algorithms.bankaccountapp.commons.OperationType;
import com.kata.algorithms.bankaccountapp.entities.Account;
import com.kata.algorithms.bankaccountapp.entities.Statement;
import com.kata.algorithms.bankaccountapp.services.AccountService;
import com.kata.algorithms.bankaccountapp.services.StatementService;

@Component
@Transactional
public class ClientOperationsController {

	@Autowired
	AccountService accountService;

	@Autowired
	StatementService statementService;

	public Account depositMoney(Account account, BigDecimal amount) {
		
		return saveStatementAndUpdateAccount(account, OperationType.CREDIT, amount);
	}

	public Account withdrawMoney(Account account, BigDecimal amount) {

		return saveStatementAndUpdateAccount(account, OperationType.DEBIT, amount);
	}

	private Account saveStatementAndUpdateAccount(Account account, OperationType type, BigDecimal amount) {
		account.setBalance(type.equals(OperationType.CREDIT) ? account.getBalance().add(amount)
				: account.getBalance().subtract(amount));
		Statement newStatement = Statement.builder().account(account).amount(amount).balance(account.getBalance())
				.type(OperationType.DEBIT).statementDate(Date.valueOf(LocalDate.now())).build();
		statementService.createStatement(newStatement);
		accountService.createOrUpdateAccount(account);
		return account;
	}

}
