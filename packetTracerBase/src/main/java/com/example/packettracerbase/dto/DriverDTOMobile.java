package com.example.packettracerbase.dto;

import com.example.packettracerbase.dto.BordoreauQRDTO;
import lombok.*;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class DriverDTOMobile {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String dateOfBirth;
    private String licenseNumber;
    private String licensePlate;
    private String brand;
    private Set<BordoreauQRDTO> bordoreauQRDTOS;

}
