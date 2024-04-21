package com.example.packettracerbase.model;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cinPacket;

    @ManyToOne
    @JoinColumn(name = "cinSender")
    private Sender sender;

    @ManyToOne
    @JoinColumn(name = "cinClient")
    private Client client;


    @ManyToMany(mappedBy = "packets")
    private Set<Driver> drivers;

    private LocalDateTime sentTime;

    // Use @ElementCollection to store the collection of strings
    @ElementCollection
    @CollectionTable(name = "medical_pieces", joinColumns = @JoinColumn(name = "cinPacket"))
    @Column(name = "medical_piece")
    private Set<String> medicalPieces;

    private String city;

    private String status;

    private String description;
}
