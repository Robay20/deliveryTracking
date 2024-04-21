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
public class Sender extends Person{
    @Id
    private String cinSender;

    private Role role = Role.Sender;

    // Define the one-to-many relationship with Packet entities
    @OneToMany(mappedBy = "sender")
    private Set<Packet> packets;
}
