package com.example.packettracerbase.model;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@MappedSuperclass
@Data
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
