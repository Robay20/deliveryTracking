package com.example.packettracerbase.dto;

import com.example.packettracerbase.model.Bordoreau;
import com.example.packettracerbase.model.Packet;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BordoreauMapper {

    public BordoreauQRDTO toBordoreauQRDTO(Bordoreau bordoreau) {
        return new BordoreauQRDTO(
                bordoreau.getBordoreau(),
                bordoreau.getDate().toString(),
                bordoreau.getLivreur().getCinDriver(),
                bordoreau.getSecteur().getIdSecteur(),
                bordoreau.getPacketsBordoreau().stream().map(this::toPacketDetailDTO).collect(Collectors.toList()),
                bordoreau.getStatus()
        );
    }

    private PacketDetailDTO toPacketDetailDTO(Packet packet) {
        return new PacketDetailDTO(
                packet.getIdPacket(),
                packet.getClient().getCinClient(),
                packet.getColis(),
                packet.getSachets(),
                packet.getStatus()
        );
    }
}
