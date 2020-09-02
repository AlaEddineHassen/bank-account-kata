package com.kata.algorithms.bankaccountapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kata.algorithms.bankaccountapp.entities.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

}
