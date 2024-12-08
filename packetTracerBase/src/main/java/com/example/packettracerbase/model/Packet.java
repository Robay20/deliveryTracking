package com.example.packettracerbase.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.Set;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "idPacket")
public class Packet {
    @Id
    private Long idPacket;

    @ManyToOne
    @JoinColumn(name = "cinClient")
    //@JsonBackReference
    private Client client;

    private int colis;

    private int sachets;

    @Enumerated(EnumType.STRING)
    private PacketStatus status;

    @ManyToOne
    //@JoinColumn(name = "bordoreau")
    private Bordoreau bordoreau;

    @OneToMany(mappedBy = "packetTransfert")
    //@JsonManagedReference
    private Set<Transfert> transferts;

    @Override
    public int hashCode() {
        return Objects.hash(idPacket);  // assuming 'id' is a unique identifier for Packet
    }
}
