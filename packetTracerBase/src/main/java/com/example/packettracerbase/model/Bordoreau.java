package com.example.packettracerbase.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Bordoreau {
    @Id
    private Long bordoreau;
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "cinDriver")
    private Driver livreur;

    @ManyToOne
    @JoinColumn(name = "idSecteur")
    private Secteur secteur;

    @OneToMany(mappedBy = "bordoreau")
    private Set<Packet> packetsBordoreau;

    @ManyToOne
    @JoinColumn(name = "cinSender")
    private Sender sender;


}
