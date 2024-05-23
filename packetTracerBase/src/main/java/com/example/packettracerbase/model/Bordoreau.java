package com.example.packettracerbase.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "bordoreau")
public class Bordoreau {
    @Id
    private Long bordoreau;
    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    private PacketStatus status;

    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "cinDriver")
    //@JsonBackReference
    private Driver livreur;

    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "idSecteur")
    //@JsonBackReference
    private Secteur secteur;

    @OneToMany(mappedBy = "bordoreau")
    //@JsonManagedReference
    private Set<Packet> packetsBordoreau;

    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "cinSender")
    //@JsonBackReference
    private Sender sender;


    @Override
    public int hashCode() {
        return Objects.hash(bordoreau);  // assuming 'id' is a unique identifier for Bordoreau
    }
}
