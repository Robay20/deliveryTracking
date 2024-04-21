package com.example.packettracerbase.service;

public interface AuthenticationService {
    boolean authenticateAdmin(String username, String password);
    boolean authenticateSender(String username, String password);
    boolean authenticateClient(String username, String password);

    boolean authenticateDriver(String username, String password);
}
