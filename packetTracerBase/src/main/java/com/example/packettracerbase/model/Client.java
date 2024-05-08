package com.example.packettracerbase.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "cinClient")
public class Client extends Person{
    @Id
    private String cinClient;

    @Builder.Default
    private final Role role = Role.Client;

    @Embedded
    private Location Location_Client;

    // Define the one-to-many relationship with Packet entities
    @OneToMany(mappedBy = "client")
    //@JsonManagedReference
    private Set<Packet> packets;

    @Override
    public int hashCode() {
        return Objects.hash(cinClient);  // assuming 'id' is a unique identifier for Packet
    }
}
