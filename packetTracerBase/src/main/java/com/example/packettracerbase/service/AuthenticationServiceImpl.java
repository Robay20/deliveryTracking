package com.example.packettracerbase.service;

import com.example.packettracerbase.model.Admin;
import com.example.packettracerbase.model.Client;
import com.example.packettracerbase.model.Driver;
import com.example.packettracerbase.model.Sender;
import com.example.packettracerbase.repository.AdminRepository;
import com.example.packettracerbase.repository.ClientRepository;
import com.example.packettracerbase.repository.DriverRepository;
import com.example.packettracerbase.repository.SenderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AdminRepository adminRepository;
    private final SenderRepository senderRepository;
    private final ClientRepository clientRepository;

    private final DriverRepository driverRepository;

    @Autowired
    public AuthenticationServiceImpl(AdminRepository adminRepository, SenderRepository senderRepository, ClientRepository clientRepository, DriverRepository driverRepository) {
        this.adminRepository = adminRepository;
        this.senderRepository = senderRepository;
        this.clientRepository = clientRepository;
        this.driverRepository = driverRepository;
    }

    @Override
    public boolean authenticateAdmin(String username, String password) {
        Optional<Admin> adminOptional = adminRepository.findByUsernameAndPassword(username, password);
        return adminOptional.isPresent();
    }

    @Override
    public boolean authenticateSender(String username, String password) {
        Optional<Sender> senderOptional = senderRepository.findByUsernameAndPassword(username, password);
        return senderOptional.isPresent();
    }

    @Override
    public boolean authenticateClient(String username, String password) {
        Optional<Client> clientOptional = clientRepository.findByUsernameAndPassword(username, password);
        return clientOptional.isPresent();
    }

    @Override
    public boolean authenticateDriver(String username, String password) {
        Optional<Driver> clientOptional = driverRepository.findByUsernameAndPassword(username, password);
        return clientOptional.isPresent();
    }
}
