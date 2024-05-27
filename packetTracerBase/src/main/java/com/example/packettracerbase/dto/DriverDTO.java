package com.example.packettracerbase.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DriverDTO {
    private String cinDriver;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate dateOfBirth;
    private String licenseNumber;
    private String licensePlate;
    private String brand;
    private String username;
    private String password;
}
