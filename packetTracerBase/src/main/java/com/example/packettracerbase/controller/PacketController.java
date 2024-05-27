package com.example.packettracerbase.controller;

import com.example.packettracerbase.model.Packet;
import com.example.packettracerbase.model.PacketStatus;
import com.example.packettracerbase.repository.PacketRepository;
import com.example.packettracerbase.service.PacketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/packets")
public class PacketController {

    private final PacketService packetService;
    private final PacketRepository packetRepository;

    @Autowired
    public PacketController(PacketService packetService, PacketRepository packetRepository) {
        this.packetService = packetService;
        this.packetRepository = packetRepository;
    }

    @GetMapping
    public ResponseEntity<List<Packet>> getAllPackets() {
        List<Packet> packets = packetService.getAllPackets();
        return new ResponseEntity<>(packets, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Packet> getPacketById(@PathVariable Long id) {
        Packet packet = packetService.getPacketById(id)
                .orElseThrow(() -> new RuntimeException("Packet not found with id: " + id));
        return new ResponseEntity<>(packet, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Packet> createPacket(@RequestBody Packet packet) {
        Packet createdPacket = packetService.createPacket(packet);
        return new ResponseEntity<>(createdPacket, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Packet> updatePacket(@PathVariable Long id, @RequestBody Packet packetDetails) {
        Packet updatedPacket = packetService.updatePacket(id, packetDetails);
        return new ResponseEntity<>(updatedPacket, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePacket(@PathVariable Long id) {
        packetService.deletePacket(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/json")
    public ResponseEntity<String> getAllPacketsAsJson() {
        String packetsJson = packetService.getAllPacketsAsJson();
        if (packetsJson != null) {
            return new ResponseEntity<>(packetsJson, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Void> updatePacketStatus(@PathVariable Long id, @RequestBody PacketStatus status) {
        try {
            Optional<Packet> packet= packetRepository.findById(id);
            packet.ifPresent(value -> packetService.updatePacket(id, value));
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
