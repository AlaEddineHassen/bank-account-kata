package com.kata.algorithms.bankaccountapp.controllers;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kata.algorithms.bankaccountapp.commons.OperationType;
import com.kata.algorithms.bankaccountapp.entities.Account;
import com.kata.algorithms.bankaccountapp.entities.Statement;
import com.kata.algorithms.bankaccountapp.exceptions.NotEnoughBalanceException;
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

		validate(account, amount, OperationType.DEBIT);

		return saveStatementAndUpdateAccount(account, OperationType.DEBIT, amount);
	}

	private void validate(Account account, BigDecimal amount, OperationType operationType) {

		if (operationType.equals(OperationType.DEBIT) && account.getBalance().subtract(amount).doubleValue() < 0) {
			throw new NotEnoughBalanceException(account, operationType);
		}

	}

	private Account saveStatementAndUpdateAccount(Account account, OperationType type, BigDecimal amount) {
		account.setBalance(type.equals(OperationType.CREDIT) ? account.getBalance().add(amount)
				: account.getBalance().subtract(amount));
		Statement newStatement = Statement.builder().account(account).amount(amount).balance(account.getBalance())
				.type(type).statementDate(Date.valueOf(LocalDate.now())).build();
		statementService.createStatement(newStatement);
		accountService.createOrUpdateAccount(account);
		return account;
	}

	public List<String> getOperationsHistory(Account account) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM uuuu");
		List<Statement> allStatmentByAccount = statementService.findStatementsByAccount(account);
		return allStatmentByAccount.stream()
				.map(statement -> String.format("%s | %s | %f | %f",
						statement.getStatementDate().toLocalDate().format(formatter), statement.getType(), statement.getAmount(),
						statement.getBalance())).collect(Collectors.toList());
	}

}
