package com.example.packet_tracer.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DriverF {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private LocalDate dateOfBirth;
    private String cinDriver;
    private String licenseNumber;
    private String licensePlate;
    private String brand;
}
