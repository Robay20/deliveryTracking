package com.example.packettracerbase.service;

import com.example.packettracerbase.model.*;
import com.example.packettracerbase.repository.*;
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
    public Optional<String> authenticateAdmin(String username, String password) {
        Optional<Admin> admin = adminRepository.findByUsernameAndPassword(username, password);
        return admin.map(Admin::getCinAdmin);
    }

    @Override
    public Optional<String> authenticateSender(String username, String password) {
        Optional<Sender> sender = senderRepository.findByUsernameAndPassword(username, password);
        return sender.map(Sender::getCinSender);
    }

    @Override
    public Optional<String> authenticateClient(String username, String password) {
        Optional<Client> client = clientRepository.findByUsernameAndPassword(username, password);
        return client.map(Client::getCinClient);
    }

    @Override
    public Optional<String> authenticateDriver(String username, String password) {
        Optional<Driver> driver = driverRepository.findByUsernameAndPassword(username, password);
        return driver.map(Driver::getCinDriver);
    }
}
