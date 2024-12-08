package com.example.packettracerbase.dto;

import com.example.packettracerbase.model.PacketStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class BordoreauQRDTO {
    private Long numeroBordoreau;
    private LocalDateTime date;
    private String stringLivreur;
    private Long codeSecteur;
    private List<PacketDetailDTO> packets;
    private PacketStatus status;
}
