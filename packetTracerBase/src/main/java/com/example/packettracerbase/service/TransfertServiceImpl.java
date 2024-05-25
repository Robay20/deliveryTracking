package com.example.packettracerbase.service;

import com.example.packettracerbase.dto.TransfertDesktop;
import com.example.packettracerbase.model.Packet;
import com.example.packettracerbase.model.Transfert;
import com.example.packettracerbase.repository.PacketRepository;
import com.example.packettracerbase.repository.TransfertRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransfertServiceImpl implements TransfertService {

    private final TransfertRepository transfertRepository;
    private final PacketRepository packetRepository;


    @Autowired
    public TransfertServiceImpl(TransfertRepository transfertRepository, PacketRepository packetRepository) {
        this.transfertRepository = transfertRepository;
        this.packetRepository = packetRepository;
    }

    @Override
    public List<Transfert> getAllTransferts() {
        return transfertRepository.findAll();
    }

    @Override
    public Optional<Transfert> getTransfertById(Long id) {
        return transfertRepository.findById(id);
    }

    @Override
    public Transfert createTransfert(Transfert transfert) {
        return transfertRepository.save(transfert);
    }

    @Override
    public Transfert updateTransfert(Long id, Transfert transfertDetails) {
        Transfert existingTransfert = transfertRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transfert not found with id: " + id));

        existingTransfert.setOldPerson(transfertDetails.getOldPerson());
        existingTransfert.setNewPerson(transfertDetails.getNewPerson());
        existingTransfert.setTime(transfertDetails.getTime());
        existingTransfert.setPacketTransfert(transfertDetails.getPacketTransfert());

        return transfertRepository.save(existingTransfert);
    }

    @Override
    public void deleteTransfert(Long id) {
        if (!transfertRepository.existsById(id)) {
            throw new EntityNotFoundException("Transfert not found with id: " + id);
        }
        transfertRepository.deleteById(id);
    }

    @Override
    public List<Transfert> getTransfersByPacketId(Long packetId) {
        Iterable<Transfert> allTransferts = transfertRepository.findAll();
        List<Transfert> matchingTransferts = new ArrayList<>();

        for (Transfert transfert : allTransferts) {
            if (transfert.getPacketTransfert() != null && transfert.getPacketTransfert().getIdPacket().equals(packetId)) {
                matchingTransferts.add(transfert);
            }
        }

        return matchingTransferts;
    }
    @Override
    public List<TransfertDesktop> getTransfersDTOByPacketId(Long packetId) {
        Iterable<Transfert> allTransferts = transfertRepository.findAll();
        List<TransfertDesktop> matchingTransferts = new ArrayList<>();

        for (Transfert transfert : allTransferts) {
            if (transfert.getPacketTransfert() != null && transfert.getPacketTransfert().getIdPacket().equals(packetId)) {
                matchingTransferts.add(convertTransfertToDTO(transfert));
            }
        }

        return matchingTransferts;
    }


    private TransfertDesktop convertTransfertToDTO(Transfert transfert) {
        TransfertDesktop dto = new TransfertDesktop();
        dto.setIdTransfert(transfert.getIdTransfert());
        dto.setOldPerson(transfert.getOldPerson());
        dto.setNewPerson(transfert.getNewPerson());
        dto.setTime(transfert.getTime());
        dto.setPacket(transfert.getPacketTransfert().getIdPacket()); // Assuming you have a getter for packet ID
        return dto;
    }

    @Override
    public String getAllTransfertsAsJson() {
        try {
            // Retrieve all Transfert objects from the database or repository
            List<Transfert> transferts = transfertRepository.findAll();

            // Convert Transfert entities to TransfertDTO instances
            List<TransfertDesktop> transfertDTOs = transferts.stream()
                    .map(this::convertTransfertToDTO)
                    .collect(Collectors.toList());

            // Serialize the TransfertDTO objects to JSON array
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(transfertDTOs);
        } catch (JsonProcessingException e) {
            e.printStackTrace(); // Handle the exception as needed
            return null;
        }
    }

    @Override
    public TransfertDesktop createTransfert(TransfertDesktop transfertDesktop) {
        System.out.println("lets see.....");
        Transfert transfert = new Transfert();
        transfert.setIdTransfert(transfertDesktop.getIdTransfert());
        transfert.setOldPerson(transfertDesktop.getOldPerson());
        transfert.setNewPerson(transfertDesktop.getNewPerson());
        transfert.setTime(transfertDesktop.getTime());
        System.out.println("we got it...");

        // Associate the Transfert with a Packet
        Long packetId = transfertDesktop.getPacket();
        Optional<Packet> optionalPacket = packetRepository.findById(packetId);
        System.out.println("mmmm things are looking exiting...");
        if (optionalPacket.isPresent()) {
            Packet packet = optionalPacket.get();
            transfert.setPacketTransfert(packet);
            System.out.println("oh packet set !!!");
        } else {
            // Handle the case when the provided packetId is not found
            // You can throw an exception or return a default/error TransfertDesktop instance
            System.out.println("No packet found with id: " + packetId);
            return null;
        }
        System.out.println("alright wer done here....");

        // Save the Transfert entity
        Transfert savedTransfert = transfertRepository.save(transfert);

        // Convert the saved Transfert object back to TransfertDesktop and return
        return convertTransfertToDTO(savedTransfert);
    }
}
