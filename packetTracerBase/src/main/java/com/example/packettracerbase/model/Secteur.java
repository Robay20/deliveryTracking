package com.example.packettracerbase.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Secteur {
    @Id
    private Long idSecteur;

    private String city;

    @OneToOne
    @JoinColumn(referencedColumnName = "cinSender")
    private Sender idSender;
}
