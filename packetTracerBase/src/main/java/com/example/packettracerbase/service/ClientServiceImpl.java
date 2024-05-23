package com.example.packettracerbase.service;

import com.example.packettracerbase.model.Client;
import com.example.packettracerbase.repository.ClientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @Override
    public Optional<Client> getClientById(String id) {
        return clientRepository.findById(id);
    }

    @Override
    public Client createClient(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public Client updateClient(String id, Client clientDetails) {
        Client existingClient = clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Client not found with id: " + id));

        // Update inherited fields from Person
        existingClient.setUsername(clientDetails.getUsername());
        existingClient.setPassword(clientDetails.getPassword());
        existingClient.setActive(clientDetails.isActive());
        existingClient.setFirstName(clientDetails.getFirstName());
        existingClient.setLastName(clientDetails.getLastName());
        existingClient.setEmail(clientDetails.getEmail());
        existingClient.setDateOfBirth(clientDetails.getDateOfBirth());

        // Update fields specific to Client
        existingClient.setLocation_Client(clientDetails.getLocation_Client());

        // Update the packets directly, assuming all necessary checks and balances are handled elsewhere
        existingClient.setPackets(clientDetails.getPackets());

        return clientRepository.save(existingClient);
    }

    @Override
    public void deleteClient(String id) {
        if (!clientRepository.existsById(id)) {
            throw new EntityNotFoundException("Client not found with id: " + id);
        }
        clientRepository.deleteById(id);
    }
}
