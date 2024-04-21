package com.example.packettracerbase.model;

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

    private Role role = Role.Driver;

    @Column(unique = true)
    private String licenseNumber;

    private String licensePlate;

    private String brand;

    @ManyToMany
    @JoinTable(
            name = "driver_packet",
            joinColumns = @JoinColumn(name = "driver_id"),
            inverseJoinColumns = @JoinColumn(name = "packet_id")
    )
    private Set<Packet> packets;

}
