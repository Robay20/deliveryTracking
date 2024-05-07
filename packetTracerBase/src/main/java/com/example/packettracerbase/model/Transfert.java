package com.example.packettracerbase.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Transfert {
    @Id
    private Long idTransfert;

    private String oldPerson;

    private String newPerson;

    private LocalDateTime time;

    @ManyToOne
    @JoinColumn(name = "bL")
    private Packet packetTransfert;
}
