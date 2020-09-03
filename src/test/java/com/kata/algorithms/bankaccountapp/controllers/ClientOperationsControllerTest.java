package com.kata.algorithms.bankaccountapp.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kata.algorithms.bankaccountapp.entities.Account;
import com.kata.algorithms.bankaccountapp.entities.Client;
import com.kata.algorithms.bankaccountapp.services.AccountService;
import com.kata.algorithms.bankaccountapp.services.ClientService;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

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
	public void withdrawSomeFromAccountTest() {
		// When Aladin want to retrieve 500 €, he makes a withdrawal from his account  with an amount of 500
		BigDecimal currentBalance = BigDecimal.valueOf(account.getBalance().doubleValue());
		Account updatedAccount = clientOperationsCtrl.withdrawMoney(account, BigDecimal.valueOf(500));

		// Then the new account's balance is the actual balance - 500

		assertEquals(updatedAccount.getBalance().doubleValue(),
				currentBalance.subtract(BigDecimal.valueOf(500)).doubleValue());
	}
}
