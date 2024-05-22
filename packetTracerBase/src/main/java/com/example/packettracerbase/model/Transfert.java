package com.example.packettracerbase.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "idTransfert")
public class Transfert {
    @Id
    private Long idTransfert;

    private String oldPerson;

    private String newPerson;

    private LocalDateTime time;

    @ManyToOne
    @JoinColumn(name = "idPacket")
    //@JsonBackReference
    private Packet packetTransfert;

    @Override
    public int hashCode() {
        return Objects.hash(idTransfert);  // assuming 'id' is a unique identifier for Packet
    }
}
