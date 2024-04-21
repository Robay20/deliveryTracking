package com.example.packettracerbase.service;

import com.example.packettracerbase.model.Packet;

import java.util.List;
import java.util.Optional;

public interface PacketService {
    List<Packet> getAllPackets();
    Optional<Packet> getPacketById(Long id);
    Packet createPacket(Packet packet);
    Packet updatePacket(Long id, Packet packetDetails);
    void deletePacket(Long id);
}
