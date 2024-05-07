package com.example.packettracerbase.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Packet {
    @Id
    private Long bL;

    @ManyToOne
    @JoinColumn(name = "cinClient")
    private Client client;

    private int colis;

    private int sachets;

    private String status;

    @ManyToOne
    @JoinColumn(name = "cinDriver")
    private Driver driver;

    @ManyToOne
    @JoinColumn(name = "bordoreau")
    private Bordoreau bordoreau;

    @OneToMany(mappedBy = "packetTransfert")
    private Set<Transfert> transferts;
}
