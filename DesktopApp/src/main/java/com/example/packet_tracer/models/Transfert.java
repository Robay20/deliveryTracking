package com.example.packet_tracer.models;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transfert {
    private Long idTransfert;
    private String oldPerson;
    private String newPerson;
    private LocalDateTime time;
    private Packet packetTransfert;
}