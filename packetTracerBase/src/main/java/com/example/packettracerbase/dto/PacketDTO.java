package com.example.packettracerbase.dto;

import com.example.packettracerbase.model.PacketStatus;
import com.example.packettracerbase.model.Transfert;
import lombok.*;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PacketDTO {
    private Long idPacket;
    private String clientCin;
    private int colis;
    private int sachets;
    private PacketStatus status;
    private Long bordoreau;

    // Constructors, getters, and setters
}