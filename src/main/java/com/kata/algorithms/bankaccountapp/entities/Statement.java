package com.kata.algorithms.bankaccountapp.entities;





import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.kata.algorithms.bankaccountapp.commons.OperationType;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Entity
public class Statement {

	@Id
	@GeneratedValue
	private Long id;

	@Column
	private BigDecimal amount;

	@Column
	@Enumerated
	private OperationType type;

	@Column
	private Date statementDate;

	@Column
	private BigDecimal balance;

	@ManyToOne(optional = false)
	private Account account;

	public Statement() {
	}

	public Statement(Long id, BigDecimal amount, OperationType type, Date statementDate, BigDecimal balance, Account account) {
		this.id = id;
		this.amount = amount;
		this.type = type;
		this.statementDate = statementDate;
		this.balance = balance;
		this.account = account;
	}

}
