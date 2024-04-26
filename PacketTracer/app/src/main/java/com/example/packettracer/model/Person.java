package com.example.packettracer.model;

import com.example.packettracer.Role;

import java.time.LocalDate;

public abstract class Person {
    protected String username;
    protected String password;
    protected boolean isActive;
    protected String firstName;
    protected String lastName;
    protected String email;
    protected Role role;
    protected LocalDate dateOfBirth;

    // Constructor
    public Person(String username, String password, boolean isActive, String firstName, String lastName, String email, Role role, LocalDate dateOfBirth) {
        this.username = username;
        this.password = password;
        this.isActive = isActive;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
        this.dateOfBirth = dateOfBirth;
    }

    // No-args constructor
    public Person() {}

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    // Optional: Override toString(), equals() and hashCode() methods if necessary
}
