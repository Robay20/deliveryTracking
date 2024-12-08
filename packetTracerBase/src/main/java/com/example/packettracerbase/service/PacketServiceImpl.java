package com.example.packettracerbase.service;

import com.example.packettracerbase.dto.PacketDTO;
import com.example.packettracerbase.model.Bordoreau;
import com.example.packettracerbase.model.Packet;
import com.example.packettracerbase.model.PacketStatus;
import com.example.packettracerbase.repository.BordoreauRepository;
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
    private final BordoreauRepository bordoreauRepository;
    private final PacketHelper packetHelper;
    private final ObjectMapper objectMapper;

    @Autowired
    public PacketServiceImpl(PacketRepository packetRepository,
                             BordoreauRepository bordoreauRepository,
                             PacketHelper packetHelper,
                             ObjectMapper objectMapper) {
        this.packetRepository = packetRepository;
        this.bordoreauRepository = bordoreauRepository;
        this.packetHelper = packetHelper;
        this.objectMapper = objectMapper;
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
        Packet existingPacket = packetHelper.findPacketById(packetRepository, id);
        packetHelper.updatePacketDetails(existingPacket, packetDetails);
        packetHelper.updateBordoreauStatusIfNeeded(packetDetails.getBordoreau(), bordoreauRepository);
        return packetRepository.save(existingPacket);
    }

    @Override
    public void deletePacket(Long id) {
        if (!packetRepository.existsById(id)) {
            throw new EntityNotFoundException("Packet not found with id: " + id);
        }
        packetRepository.deleteById(id);
    }

    @Override
    public String getAllPacketsAsJson() {
        try {
            List<PacketDTO> packetDTOs = packetRepository.findAll()
                    .stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
            return objectMapper.writeValueAsString(packetDTOs);
        } catch (JsonProcessingException e) {
            packetHelper.logError("Error converting packets to JSON", e);
            return null;
        }
    }

    private PacketDTO convertToDTO(Packet packet) {
        PacketDTO dto = new PacketDTO();
        dto.setIdPacket(packet.getIdPacket());
        dto.setClientCin(packet.getClient().getCinClient());
        dto.setColis(packet.getColis());
        dto.setSachets(packet.getSachets());
        dto.setStatus(packet.getStatus());
        dto.setBordoreau(packet.getBordoreau().getBordoreau());
        return dto;
    }
}