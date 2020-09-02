package com.kata.algorithms.bankaccountapp.services;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kata.algorithms.bankaccountapp.entities.Client;

@SpringBootTest
public class ClientServiceTest {
	
	@Autowired
	ClientService clientService;

	@Test
	public void createClientTest() {
		
		//Given
		Client client = new Client("Aladin", "Hassan");
		
		//When
		
		Client createClient = clientService.createOrUpdateClient(client);
		
		//Then
		assertNotNull(createClient);
		// assertNotNull(createClient.getId());
		
		
	}
}
