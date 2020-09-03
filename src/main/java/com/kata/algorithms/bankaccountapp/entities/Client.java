package com.kata.algorithms.bankaccountapp.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Entity
public class Client {

	@Id
	@GeneratedValue
	private Long id;

	@Column
	private String firstName;

	@Column
	private String lastName;

	@OneToOne
	private Account account;

	public Client() {

	}

	public Client(Long id, String firstName, String lastName, Account account) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.account = account;
	}

}
