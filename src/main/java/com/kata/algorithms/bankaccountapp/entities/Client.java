package com.kata.algorithms.bankaccountapp.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Client {

	@Id
	@GeneratedValue
	private Long id;

	@Column
	private String firstName;
	
	@Column
	private String lastName;

	public Client(String firstName, String lastName) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Client() {
		
	}
	
	
	
}