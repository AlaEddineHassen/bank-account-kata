package com.kata.algorithms.bankaccountapp.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kata.algorithms.bankaccountapp.entities.Client;
import com.kata.algorithms.bankaccountapp.repositories.ClientRepository;

@Component
public class ClientService {
	
	@Autowired
	ClientRepository clientRepository;

	public Client createOrUpdateClient(Client client) {
		return clientRepository.save(client);
	}

	public Optional<Client> findById(Long id) {
		
		return clientRepository.findById(id);
	}

	public void remove(Client client) {
		
		clientRepository.delete(client);
	}

}
