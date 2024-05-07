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
        Packet existingPacket = packetRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Packet not found with id: " + id));

        // Update packet details here based on the attributes in the Packet model
        existingPacket.setClient(packetDetails.getClient());       // Update the client
        existingPacket.setColis(packetDetails.getColis());         // Update the number of colis
        existingPacket.setSachets(packetDetails.getSachets());     // Update the number of sachets
        existingPacket.setStatus(packetDetails.getStatus());       // Update the status
        existingPacket.setBordoreau(packetDetails.getBordoreau()); // Update the bordoreau

        // Assuming transferts are to be handled separately or are updated through a different mechanism,
        // as directly setting a complex relationship collection can be problematic.
        existingPacket.setTransferts(packetDetails.getTransferts());

        return packetRepository.save(existingPacket);
    }

    @Override
    public void deletePacket(Long id) {
        if (!packetRepository.existsById(id)) {
            throw new EntityNotFoundException("Packet not found with id: " + id);
        }
        packetRepository.deleteById(id);
    }
}
