package com.example.packettracerbase.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "cinDriver")
public class Driver extends Person{
    @Id
    private String cinDriver;

    @Builder.Default
    private final Role role = Role.Driver;

    @Column(unique = true)
    private String licenseNumber;

    private String licensePlate;

    private String brand;

    @OneToMany(mappedBy = "livreur")
    //@JsonManagedReference
    private Set<Bordoreau> bordoreausDriver;


    @Override
    public int hashCode() {
        return Objects.hash(cinDriver);  // assuming 'id' is a unique identifier for Packet
    }
}
