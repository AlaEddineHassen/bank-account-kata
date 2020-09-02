package com.kata.algorithms.bankaccountapp.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.kata.algorithms.bankaccountapp.entities.Account;
import com.kata.algorithms.bankaccountapp.repositories.AccountRepository;

@Component
public class AccountService {

	@Autowired
	AccountRepository accountRepository;

	public Account createOrUpdateAccount(Account account) {
		return accountRepository.save(account);
	}

	public Optional<Account> findAccountById(Long id) {
		return accountRepository.findById(id);
	}

}
