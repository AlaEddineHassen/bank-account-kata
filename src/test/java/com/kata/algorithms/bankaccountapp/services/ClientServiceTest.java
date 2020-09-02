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

		// Given
		Client client = Client.builder().firstName("Aladin").lastName("Hassan").build();

		// When

		Client createdClient = clientService.createOrUpdateClient(client);

		// Then
		assertNotNull(createdClient);
		assertNotNull(createdClient.getId());
		assertEquals(client.getFirstName(), createdClient.getFirstName());
		assertEquals(client.getLastName(), createdClient.getLastName());

	}

	@Test
	public void findClientByIdTest() {
		// Given
		Client client = Client.builder().firstName("Aladin").lastName("Hassan").build();

		// When

		Client createdClient = clientService.createOrUpdateClient(client);
		Client foundClient = clientService.findById(createdClient.getId()).orElse(Client.builder().build());

		// Then
		assertNotNull(foundClient.getId());
	}

	@Test
	void updateClientTest() {

		// Given
		Client client = Client.builder().firstName("Aladin").lastName("Hassan").build();

		// When

		Client createdClient = clientService.createOrUpdateClient(client);
		createdClient.setLastName("Hassan Updated");
		Client updatedClient = clientService.createOrUpdateClient(createdClient);

		// Then
		assertEquals(createdClient.getId(), updatedClient.getId());
		assertEquals(createdClient.getLastName(), updatedClient.getLastName());

	}

	@Test
	public void removeClientTest() {
		// Given
		Client client = Client.builder().firstName("Aladin").lastName("Hassan").build();
		System.out.println("client id " + client.getId());

		// When

		Client createdClient = clientService.createOrUpdateClient(client);
		clientService.remove(createdClient);
		System.out.println("client id " + client.getId());
		Client foundClinet = clientService.findById(createdClient.getId()).orElse(Client.builder().build());

		// Then
		assertNull(foundClinet.getId());

	}

}
