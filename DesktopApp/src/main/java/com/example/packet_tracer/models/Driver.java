package com.example.packet_tracer.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Driver extends Person {
    private String cinDriver;
    private Role role;
    private String licenseNumber;
    private String licensePlate;
    private String brand;
    private Set<Bordoreau> bordoreausDriver;
}