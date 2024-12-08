package com.example.packettracerbase.model;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@MappedSuperclass
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class Person {

    protected String username;

    protected String password;

    protected boolean isActive;

    protected String firstName;

    protected String lastName;

    protected String email;

    protected Role role;

    @Temporal(TemporalType.DATE)
    protected LocalDate dateOfBirth;


}
