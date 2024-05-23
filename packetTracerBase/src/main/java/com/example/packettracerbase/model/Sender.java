package com.example.packettracerbase.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
        property = "cinSender")
public class Sender extends Person{
    @Id
    private String cinSender;

    @Builder.Default
    private final Role role = Role.Sender;

    // Define the one-to-many relationship with Packet entities
    @OneToMany(mappedBy = "sender")
    //@JsonManagedReference
    private Set<Bordoreau> bordoreausSender;

    @OneToOne(mappedBy = "idSender")
    //@JsonBackReference
    private Secteur secteur;

    @Override
    public int hashCode() {
        return Objects.hash(cinSender);  // assuming 'id' is a unique identifier for Packet
    }
}
