package com.kata.algorithms.bankaccountapp.entities;

import java.util.Currency;
import java.util.Locale;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import lombok.Builder;
import lombok.Data;

@Data
@Entity
@Builder
public class Account {

	@Id
	@GeneratedValue
	private Long id;

	@Column
	private String label;

	@Column
	private Double balance;

	@OneToOne(optional = false)
	private Client client;

	@Builder.Default
	private Currency currency = Currency.getInstance(Locale.getDefault());

	public Account(Long id, String label, Double balance, Client client, Currency currency) {
		super();
		this.id = id;
		this.label = label;
		this.balance = balance;
		this.client = client;
		this.currency = currency;
	}

	public Account(String label, Double balance, Client client, Currency currency) {
		super();
		this.label = label;
		this.balance = balance;
		this.client = client;
		this.currency = currency;
	}

	public Account() {

	}

}
