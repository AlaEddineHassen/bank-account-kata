package com.kata.algorithms.bankaccountapp.services;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kata.algorithms.bankaccountapp.entities.Account;
import com.kata.algorithms.bankaccountapp.entities.Client;

@SpringBootTest
public class AccountServiceTest {

	@Autowired
	AccountService accountService;
	@Autowired
	ClientService clientService;

	@Test
	public void createAccountTest() {
		// Given
		Client client = Client.builder().firstName("Aladin").lastName("Hassan").build();
		clientService.createOrUpdateClient(client);

		Account account = Account.builder().label("AHCASHACC").balance(BigDecimal.valueOf(0)).client(client).build();

		// When
		Account createdAccount = accountService.createOrUpdateAccount(account);

		// Then
		assertNotNull(createdAccount.getId());

	}

	@Test
	public void findAccountById() {

		// Given
		Client client = Client.builder().firstName("Aladin").lastName("Hassan").build();
		clientService.createOrUpdateClient(client);

		Account account = Account.builder().label("AHCASHACC").balance(BigDecimal.valueOf(0)).client(client).build();
		 accountService.createOrUpdateAccount(account);
		// When
		 Account foundAccount = accountService.findAccountById(account.getId()).orElse(null);
		// Then
		assertNotNull(foundAccount);

	}

	@Test
	public void updateAccount() {
		// Given
		Client client = Client.builder().firstName("Aladin").lastName("Hassan").build();
		clientService.createOrUpdateClient(client);

		Account account = Account.builder().label("AHCASHACC").balance(BigDecimal.valueOf(0)).client(client).build();
		Account createdAccount = accountService.createOrUpdateAccount(account);
		// When

		createdAccount.setBalance(BigDecimal.valueOf(1000000));
		Account updatedAccount = accountService.createOrUpdateAccount(createdAccount);
		Account foundAccount = accountService.findAccountById(updatedAccount.getId()).orElse(null);

		// Then
		assertNotNull(foundAccount);
		assertEquals(updatedAccount.getBalance().doubleValue(), foundAccount.getBalance().doubleValue());

	}

}
