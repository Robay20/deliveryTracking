package com.example.packettracerbase.model;

import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Admin extends Person{
    @Id
    private String cinAdmin;

    private Role role = Role.Admin;
}
