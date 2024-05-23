package com.example.packettracerbase.dto;

import com.example.packettracerbase.model.PacketStatus;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PacketDetailDTO {
    private Long numeroBL;
    private String codeClient;
    private int nbrColis;
    private int nbrSachets;
    private PacketStatus status;  // Add status field to hold the packet status

}
