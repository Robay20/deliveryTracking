package com.example.packettracer.model;

import com.example.packettracer.Role;

import java.time.LocalDate;
import java.util.Set;



public class Driver extends Person {
    private String cinDriver;
    private String licenseNumber;
    private String licensePlate;

    private final Role role =Role.Driver;
    private String brand;
    private Set<Packet> packets;
    private Set<Route> routes;

    // Updated constructor that includes the superclass fields
    public Driver(String username, String password, boolean isActive, String firstName, String lastName, String email, Role role, LocalDate dateOfBirth,
                  String cinDriver, String licenseNumber, String licensePlate, String brand, Set<Packet> packets, Set<Route> routes) {
        // Call to the superclass (Person) constructor
        super(username, password, isActive, firstName, lastName, email, role, dateOfBirth);

        // Initialization of Driver-specific fields
        this.cinDriver = cinDriver;
        this.licenseNumber = licenseNumber;
        this.licensePlate = licensePlate;
        this.brand = brand;
        this.packets = packets;
        this.routes = routes;
    }

    // Getters and setters for Driver-specific fields
    public String getCinDriver() {
        return cinDriver;
    }

    public void setCinDriver(String cinDriver) {
        this.cinDriver = cinDriver;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Set<Packet> getPackets() {
        return packets;
    }

    public void setPackets(Set<Packet> packets) {
        this.packets = packets;
    }

    public Set<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(Set<Route> routes) {
        this.routes = routes;
    }

    // Optionally override toString(), equals() and hashCode() methods if necessary
    @Override
    public String toString() {
        return "Driver{" +
                "cinDriver='" + cinDriver + '\'' +
                ", licenseNumber='" + licenseNumber + '\'' +
                ", licensePlate='" + licensePlate + '\'' +
                ", brand='" + brand + '\'' +
                ", packets=" + packets +
                ", routes=" + routes +
                "} " + super.toString();
    }
}
