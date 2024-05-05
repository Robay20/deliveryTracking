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

    //@JsonIgnore
    //@JsonManagedReference
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "driver_packet",
            joinColumns = @JoinColumn(name = "driver_id"),
            inverseJoinColumns = @JoinColumn(name = "packet_id")
    )
    @EqualsAndHashCode.Exclude
    private Set<Packet> packets;

    //@JsonIgnore
    //@JsonManagedReference
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "driver_route",
            joinColumns = @JoinColumn(name = "driver_id"),
            inverseJoinColumns = @JoinColumn(name = "route_id")
    )
    @EqualsAndHashCode.Exclude
    private Set<Route> routes;

}
