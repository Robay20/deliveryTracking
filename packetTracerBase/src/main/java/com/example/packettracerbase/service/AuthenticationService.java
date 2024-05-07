package com.example.packettracerbase.service;

import java.util.Optional;

public interface AuthenticationService {
    Optional<String> authenticateAdmin(String username, String password);
    Optional<String> authenticateSender(String username, String password);
    Optional<String> authenticateClient(String username, String password);
    Optional<String> authenticateDriver(String username, String password);
}
