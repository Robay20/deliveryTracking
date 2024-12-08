package com.example.packettracerbase.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;

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
