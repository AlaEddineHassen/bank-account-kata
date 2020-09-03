package com.kata.algorithms.bankaccountapp.exceptions;

import com.kata.algorithms.bankaccountapp.commons.OperationType;
import com.kata.algorithms.bankaccountapp.entities.Account;

public class NotEnoughBalanceException extends RuntimeException {
	
	private static final String MESSAGE = "Unauthorized operation %s on your account %s, your current balance is %f";

    public NotEnoughBalanceException(Account account, OperationType operationType) {
        super(String.format(MESSAGE, operationType, account.getLabel(),account.getBalance()));
    }

}
