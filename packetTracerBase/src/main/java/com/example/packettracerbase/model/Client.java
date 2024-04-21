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
public class Client extends Person{
    @Id
    private String cinClient;

    private Role role = Role.Client;

    @Embedded
    private Location Location_Client;

    // Define the one-to-many relationship with Packet entities
    @OneToMany(mappedBy = "client")
    private Set<Packet> packets;
}
