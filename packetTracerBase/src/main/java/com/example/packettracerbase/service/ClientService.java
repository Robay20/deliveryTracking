package com.example.packettracerbase.service;

import com.example.packettracerbase.model.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {
    List<Client> getAllClients();
    Optional<Client> getClientById(String id);
    Client createClient(Client client);
    Client updateClient(String id, Client clientDetails);
    void deleteClient(String id);
}
