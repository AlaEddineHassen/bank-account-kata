package com.kata.algorithms.bankaccountapp.services;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.kata.algorithms.bankaccountapp.commons.OperationType;
import com.kata.algorithms.bankaccountapp.entities.Account;
import com.kata.algorithms.bankaccountapp.entities.Client;
import com.kata.algorithms.bankaccountapp.entities.Statement;

@SpringBootTest
public class StatementServiceTest {

	@Autowired
	StatementService statementService;
	@Autowired
	ClientService clientService;
	@Autowired
	AccountService accountService;

	static Client client;
	static Account account;

	@PostConstruct
	public void setup() {

		client = Client.builder().firstName("Aladin").lastName("Hassan").build();
		clientService.createOrUpdateClient(client);
		account = Account.builder().label("AHCASHACC").balance(Double.valueOf(0)).client(client).build();
		accountService.createOrUpdateAccount(account);
	}

	@Test
	@DirtiesContext
	public void createStatementTest() {

		Statement statement = Statement.builder().amount(Double.valueOf(1000)).balance(Double.valueOf(1000))
				.statementDate(Date.valueOf(LocalDate.now())).type(OperationType.CREDIT).account(account).build();
		statementService.createStatement(statement);

		assertNotNull(statement.getId());
	}

	@Test
	@DirtiesContext
	public void findAllStatementTest() {
		// Given
		Statement creditStatement = Statement.builder().amount(Double.valueOf(1000)).balance(Double.valueOf(1000))
				.statementDate(Date.valueOf(LocalDate.now())).type(OperationType.CREDIT).account(account).build();
		statementService.createStatement(creditStatement);
		Statement debitStatement = Statement.builder().amount(Double.valueOf(1000)).balance(Double.valueOf(1000))
				.statementDate(Date.valueOf(LocalDate.now())).type(OperationType.DEBIT).account(account).build();
		statementService.createStatement(debitStatement);

		// When
		List<Statement> statements = statementService.findAllStatement();

		// Then
		assertNotNull(statements);
		assertEquals(statements.size(), 2);
	}

	@Test
	@DirtiesContext
	public void findStatementByAccount() {
		// Given
		Statement creditStatement = Statement.builder().amount(Double.valueOf(1000)).balance(Double.valueOf(1000))
				.statementDate(Date.valueOf(LocalDate.now())).type(OperationType.CREDIT).account(account).build();
		statementService.createStatement(creditStatement);
		Statement debitStatement = Statement.builder().amount(Double.valueOf(1000)).balance(Double.valueOf(1000))
				.statementDate(Date.valueOf(LocalDate.now())).type(OperationType.DEBIT).account(account).build();
		statementService.createStatement(debitStatement);

		// When
		List<Statement> statements = statementService.findStatementsByAccount(account);

		// Then
		assertNotNull(statements);
		assertEquals(statements.size(), 2);
	}

	@Test
	@DirtiesContext
	public void findStatementByAccountByType() {
		// Given
		Statement creditStatement = Statement.builder().amount(Double.valueOf(1000)).balance(Double.valueOf(1000))
				.statementDate(Date.valueOf(LocalDate.now())).type(OperationType.CREDIT).account(account).build();
		statementService.createStatement(creditStatement);

		Statement sndCreditStatement = Statement.builder().amount(Double.valueOf(1000)).balance(Double.valueOf(1000))
				.statementDate(Date.valueOf(LocalDate.now())).type(OperationType.CREDIT).account(account).build();
		statementService.createStatement(sndCreditStatement);
		
		Statement debitStatement = Statement.builder().amount(Double.valueOf(1000)).balance(Double.valueOf(1000))
				.statementDate(Date.valueOf(LocalDate.now())).type(OperationType.DEBIT).account(account).build();
		statementService.createStatement(debitStatement);

		// When
		List<Statement> creditStatements = statementService.findByAccountAndOperationType(account,
				OperationType.CREDIT);
		List<Statement> debitStatements = statementService.findByAccountAndOperationType(account, OperationType.DEBIT);

		// Then
		assertNotNull(creditStatements);
		assertEquals(creditStatements.size(), 2);
		assertNotNull(debitStatements);
		assertEquals(debitStatements.size(), 1);
	}

}
