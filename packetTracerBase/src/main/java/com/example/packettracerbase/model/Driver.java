package com.example.packettracerbase.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Driver extends Person{
    @Id
    private String cinDriver;

    @Builder.Default
    private final Role role = Role.Driver;

    @Column(unique = true)
    private String licenseNumber;

    private String licensePlate;

    private String brand;

    @OneToMany(mappedBy = "driver")
    private Set<Packet> packetsDriver;

    @OneToMany(mappedBy = "livreur")
    private Set<Bordoreau> bordoreausDriver;
}
