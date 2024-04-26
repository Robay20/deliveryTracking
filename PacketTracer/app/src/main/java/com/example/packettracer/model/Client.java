package com.example.packettracer.model;

import com.example.packettracer.Role;

import java.time.LocalDate;
import java.util.Set;

public class Client extends Person {
    private String cinClient;
    private final Role role = Role.Client; // Default to Role.Client
    private Location locationClient; // Assuming Location is another class you have defined
    private Set<Packet> packets;

    // No-args constructor
    public Client() {
        super();
    }

    // All-args constructor
    public Client(String username, String password, boolean isActive, String firstName, String lastName, String email, Role role, LocalDate dateOfBirth, String cinClient,Location locationClient, Set<Packet> packets) {
        super(username, password, isActive, firstName, lastName, email, role, dateOfBirth);
        this.cinClient = cinClient;
        this.locationClient = locationClient;
        this.packets = packets;
    }

    // Getters and setters
    public String getCinClient() {
        return cinClient;
    }

    public void setCinClient(String cinClient) {
        this.cinClient = cinClient;
    }

    public Role getRole() {
        return role;
    }

    public Location getLocationClient() {
        return locationClient;
    }

    public void setLocationClient(Location locationClient) {
        this.locationClient = locationClient;
    }

    public Set<Packet> getPackets() {
        return packets;
    }

    public void setPackets(Set<Packet> packets) {
        this.packets = packets;
    }

    // Optionally override toString(), equals() and hashCode() if necessary
    @Override
    public String toString() {
        return "Client{" +
                "cinClient='" + cinClient + '\'' +
                ", role=" + role +
                ", locationClient=" + locationClient +
                ", packets=" + packets +
                "} " + super.toString();
    }
}