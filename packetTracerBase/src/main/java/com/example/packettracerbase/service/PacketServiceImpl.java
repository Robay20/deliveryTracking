package com.example.packettracerbase.service;

import com.example.packettracerbase.dto.PacketDTO;
import com.example.packettracerbase.model.Packet;
import com.example.packettracerbase.repository.PacketRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    private PacketDTO convertToDTO(Packet packet) {
        PacketDTO dto = new PacketDTO();
        dto.setIdPacket(packet.getIdPacket());
        dto.setClientCin(packet.getClient().getCinClient());
        dto.setColis(packet.getColis());
        dto.setSachets(packet.getSachets());
        dto.setStatus(packet.getStatus());
        dto.setBordoreau(packet.getBordoreau().getBordoreau());
        dto.setTransferts(packet.getTransferts());
        return dto;
    }
    @Override
    public String getAllPacketsAsJson() {
        try {
            // Retrieve all Packet objects from the database or repository
            List<Packet> packets = packetRepository.findAll();

            // Convert Packet entities to PacketDTO instances
            List<PacketDTO> packetDTOs = packets.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());

            // Serialize the PacketDTO objects to JSON array
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(packetDTOs);
        } catch (JsonProcessingException e) {
            e.printStackTrace(); // Handle the exception as needed
            return null;
        }
    }
}
