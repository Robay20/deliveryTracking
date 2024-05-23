package com.example.packettracer.model;

import java.util.List;
import java.util.Objects;

public class Driver {
    String username;
    String password; // Generally, it's not recommended to fetch passwords.
    String firstName;
    String lastName;
    String email;
    String dateOfBirth;
    String licenseNumber;
    String licensePlate;
    String brand;
    List<Bordoreau> bordoreauQRDTOS;

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

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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

    public List<Bordoreau> getBordoreauQRDTOS() {
        return bordoreauQRDTOS;
    }

    public void setBordoreauQRDTOS(List<Bordoreau> bordoreauQRDTOS) {
        this.bordoreauQRDTOS = bordoreauQRDTOS;
    }

    public Driver(String username, String password, String firstName, String lastName, String email, String dateOfBirth, String licenseNumber, String licensePlate, String brand, List<Bordoreau> bordoreauQRDTOS) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.licenseNumber = licenseNumber;
        this.licensePlate = licensePlate;
        this.brand = brand;
        this.bordoreauQRDTOS = bordoreauQRDTOS;
    }

    public Driver() {
    }

    @Override
    public String toString() {
        return "Driver{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", licenseNumber='" + licenseNumber + '\'' +
                ", licensePlate='" + licensePlate + '\'' +
                ", brand='" + brand + '\'' +
                ", bordoreauQRDTOS=" + bordoreauQRDTOS.toString() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Driver)) return false;
        Driver driver = (Driver) o;
        return Objects.equals(getUsername(), driver.getUsername()) && Objects.equals(getPassword(), driver.getPassword()) && Objects.equals(getFirstName(), driver.getFirstName()) && Objects.equals(getLastName(), driver.getLastName()) && Objects.equals(getEmail(), driver.getEmail()) && Objects.equals(getDateOfBirth(), driver.getDateOfBirth()) && Objects.equals(getLicenseNumber(), driver.getLicenseNumber()) && Objects.equals(getLicensePlate(), driver.getLicensePlate()) && Objects.equals(getBrand(), driver.getBrand()) && Objects.equals(getBordoreauQRDTOS(), driver.getBordoreauQRDTOS());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername(), getPassword(), getFirstName(), getLastName(), getEmail(), getDateOfBirth(), getLicenseNumber(), getLicensePlate(), getBrand(), getBordoreauQRDTOS());
    }
}
