package com.example.packettracerbase.service;

import com.example.packettracerbase.model.Packet;
import com.example.packettracerbase.repository.PacketRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacketServiceImpl implements PacketService {

    private final PacketRepository packetRepository;

    @Autowired
    public PacketServiceImpl(PacketRepository packetRepository) {
        this.packetRepository = packetRepository;
    }

    @Override
    public List<Packet> getAllPackets() {
        return packetRepository.findAll();
    }

    @Override
    public Optional<Packet> getPacketById(Long id) {
        return packetRepository.findById(id);
    }

    @Override
    public Packet createPacket(Packet packet) {
        return packetRepository.save(packet);
    }

    @Override
    public Packet updatePacket(Long id, Packet packetDetails) {
        Packet packet = packetRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Packet not found with id: " + id));

        // Update packet details here
        packet.setSender(packetDetails.getSender());
        packet.setClient(packetDetails.getClient());
        packet.setDrivers(packetDetails.getDrivers());
        packet.setSentTime(packetDetails.getSentTime());
        packet.setMedicalPieces(packetDetails.getMedicalPieces());
        packet.setCity(packetDetails.getCity());
        packet.setStatus(packetDetails.getStatus());
        packet.setDescription(packetDetails.getDescription());
        // Set other fields as needed

        return packetRepository.save(packet);
    }

    @Override
    public void deletePacket(Long id) {
        if (!packetRepository.existsById(id)) {
            throw new EntityNotFoundException("Packet not found with id: " + id);
        }
        packetRepository.deleteById(id);
    }
}
