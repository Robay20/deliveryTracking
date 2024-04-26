package com.example.packettracer.model;

import com.example.packettracer.Role;

import java.time.LocalDate;
import java.util.Set;

public class Sender extends Person {
    private String cinSender;
    private final Role role = Role.Sender;
    private Set<Packet> packets;

    // No-args constructor
    public Sender() {
        super();
    }

    // All-args constructor
    public Sender(String username, String password, boolean isActive, String firstName, String lastName, String email, Role role, LocalDate dateOfBirth, String cinSender, Set<Packet> packets) {
        super(username, password, isActive, firstName, lastName, email, role, dateOfBirth);
        this.cinSender = cinSender;
        this.packets = packets;
    }

    // Getters and setters
    public String getCinSender() {
        return cinSender;
    }

    public void setCinSender(String cinSender) {
        this.cinSender = cinSender;
    }

    public Role getRole() {
        return role;
    }


    public Set<Packet> getPackets() {
        return packets;
    }

    public void setPackets(Set<Packet> packets) {
        this.packets = packets;
    }

    // Optionally override toString(), equals() and hashCode() methods if necessary
    @Override
    public String toString() {
        return "Sender{" +
                "cinSender='" + cinSender + '\'' +
                ", role=" + role +
                ", packets=" + packets +
                "} " + super.toString();
    }
}
