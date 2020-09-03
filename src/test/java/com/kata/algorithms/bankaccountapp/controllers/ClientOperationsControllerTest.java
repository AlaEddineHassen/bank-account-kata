package com.kata.algorithms.bankaccountapp.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.kata.algorithms.bankaccountapp.entities.Account;
import com.kata.algorithms.bankaccountapp.entities.Client;
import com.kata.algorithms.bankaccountapp.exceptions.NotEnoughBalanceException;
import com.kata.algorithms.bankaccountapp.services.AccountService;
import com.kata.algorithms.bankaccountapp.services.ClientService;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.PostConstruct;

@SpringBootTest
public class ClientOperationsControllerTest {

	@Autowired
	ClientOperationsController clientOperationsCtrl;

	@Autowired
	ClientService clientService;
	@Autowired
	AccountService accountService;

	static Client client;
	static Account account;

	@PostConstruct
	public void setup() {
		// Given: a Client Aladin having an account AHCASHACC
		client = Client.builder().firstName("Aladin").lastName("Hassan").build();
		clientService.createOrUpdateClient(client);
		account = Account.builder().label("AHCASHACC").balance(BigDecimal.valueOf(10000)).client(client).build();
		accountService.createOrUpdateAccount(account);
		client.setAccount(account);
		clientService.createOrUpdateClient(client);
	}

	/**
	 * US 1: In order to save money As a bank client I want to make a deposit in my
	 * account
	 **/

	@Test
	@DirtiesContext
	public void depositToAccountTest() {

		// When Aladin want to save 500 €, he makes a deposit whith an amount of 500
		BigDecimal currentBalance = BigDecimal.valueOf(account.getBalance().doubleValue());
		Account updatedAccount = clientOperationsCtrl.depositMoney(account, BigDecimal.valueOf(500));

		// Then the new account's balance is the actual balance + 500

		assertEquals(updatedAccount.getBalance().doubleValue(),
				currentBalance.add(BigDecimal.valueOf(500)).doubleValue());

	}

	/*
	 * US 2: In order to retrieve some or all of my savings As a bank client I want
	 * to make a withdrawal from my account
	 */
	@Test
	@DirtiesContext
	public void withdrawSomeFromAccountTest() {
		// When Aladin want to retrieve 500 €, he makes a withdrawal from his account
		// with an amount of 500
		BigDecimal currentBalance = BigDecimal.valueOf(account.getBalance().doubleValue());
		Account updatedAccount = clientOperationsCtrl.withdrawMoney(account, BigDecimal.valueOf(500));

		// Then the new account's balance is the actual balance - 500

		assertEquals(updatedAccount.getBalance().doubleValue(),
				currentBalance.subtract(BigDecimal.valueOf(500)).doubleValue());
	}

	@Test
	@DirtiesContext
	public void withdrawMoreThanTheBalanceTest() {
		// When Aladin want to retrieve 1500 €, he makes a withdrawal from his account
		// with an amount of 1500 But he doesn't have an enough balance in his account

		// Then the new account's balance is the actual balance - 500

		assertThrows(NotEnoughBalanceException.class, () -> {
			clientOperationsCtrl.withdrawMoney(account, BigDecimal.valueOf(15000));
		});

	}

	/*
	 * US3 : In order to check my operations As a bank client I want to see the
	 * history (operation, date, amount, balance) of my operations
	 */
	@Test
	@DirtiesContext
	public void checkTheAccountHistoryTest() {
		// Given : Aladin a client making 3 deposits and 2 withdrawals operations
		 clientOperationsCtrl.depositMoney(account, BigDecimal.valueOf(3500));
		 clientOperationsCtrl.depositMoney(account, BigDecimal.valueOf(1500));
		 clientOperationsCtrl.depositMoney(account, BigDecimal.valueOf(300));
		 clientOperationsCtrl.withdrawMoney(account, BigDecimal.valueOf(500));
		 clientOperationsCtrl.withdrawMoney(account, BigDecimal.valueOf(400));
		
		// When he wants to see his operations history
		 List<String> history = clientOperationsCtrl.getOperationsHistory(account);

         // Then 
		 assertEquals(history.size(), 5);
		 assertEquals(history.get(0), "03 Sep 2020 | CREDIT | 3500.000000 | 13500.000000");
		 assertEquals(history.get(1), "03 Sep 2020 | CREDIT | 1500.000000 | 15000.000000");
		 assertEquals(history.get(2), "03 Sep 2020 | CREDIT | 300.000000 | 15300.000000");
		 assertEquals(history.get(3), "03 Sep 2020 | DEBIT | 500.000000 | 14800.000000");
		 assertEquals(history.get(4), "03 Sep 2020 | DEBIT | 400.000000 | 14400.000000");
		
		 
	}
}
