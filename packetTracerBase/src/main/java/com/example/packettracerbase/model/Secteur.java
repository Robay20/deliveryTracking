package com.example.packettracerbase.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "idSecteur")
public class Secteur {
    @Id
    private Long idSecteur;

    private String city;

    @OneToOne
    @JoinColumn(referencedColumnName = "cinSender")
    //@JsonManagedReference
    private Sender idSender;

    @Override
    public int hashCode() {
        return Objects.hash(idSecteur);  // assuming 'id' is a unique identifier for Packet
    }
}
