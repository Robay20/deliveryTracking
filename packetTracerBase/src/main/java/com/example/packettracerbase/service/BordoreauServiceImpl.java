package com.example.packettracerbase.service;

import com.example.packettracerbase.dto.BordoreauQRDTO;
import com.example.packettracerbase.dto.PacketDetailDTO;
import com.example.packettracerbase.dto.UpdateBordoreauRequest;
import com.example.packettracerbase.model.*;
import com.example.packettracerbase.repository.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BordoreauServiceImpl implements BordoreauService {

    private final BordoreauRepository bordoreauRepository;
    private final PacketRepository packetRepository;
    private final ClientRepository clientRepository;
    private final SenderRepository senderRepository;
    private final SecteurRepository secteurRepository;
    private final DriverRepository driverRepository;

    @Autowired
    public BordoreauServiceImpl(BordoreauRepository bordoreauRepository, PacketRepository packetRepository, ClientRepository clientRepository, SenderRepository senderRepository, SecteurRepository secteurRepository, DriverRepository driverRepository) {
        this.bordoreauRepository = bordoreauRepository;
        this.packetRepository = packetRepository;
        this.clientRepository = clientRepository;
        this.senderRepository = senderRepository;
        this.secteurRepository = secteurRepository;
        this.driverRepository = driverRepository;
    }

    @Override
    public List<Bordoreau> getAllBordoreaux() {
        return bordoreauRepository.findAll();
    }

    @Override
    public Optional<Bordoreau> getBordoreauById(Long id) {
        return bordoreauRepository.findById(id);
    }


    @Override
    public Bordoreau createBordoreau(Bordoreau bordoreau) {
        return bordoreauRepository.save(bordoreau);
    }

    @Override
    public Bordoreau updateBordoreau(Long id, Bordoreau bordoreauDetails) {
        Bordoreau existingBordoreau = bordoreauRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Bordoreau not found with id: " + id));

        existingBordoreau.setDate(bordoreauDetails.getDate());
        existingBordoreau.setLivreur(bordoreauDetails.getLivreur());
        existingBordoreau.setSecteur(bordoreauDetails.getSecteur());
        existingBordoreau.setPacketsBordoreau(bordoreauDetails.getPacketsBordoreau());
        existingBordoreau.setSender(bordoreauDetails.getSender());

        return bordoreauRepository.save(existingBordoreau);
    }

    @Override
    public void deleteBordoreau(Long id) {
        if (!bordoreauRepository.existsById(id)) {
            throw new EntityNotFoundException("Bordoreau not found with id: " + id);
        }
        bordoreauRepository.deleteById(id);
    }
    @Override
    public BordoreauQRDTO getBordoreauForQR(Long bordoreauId) {
        Bordoreau bordoreau = bordoreauRepository.findById(bordoreauId)
                .orElseThrow(() -> new RuntimeException("Bordoreau not found with id: " + bordoreauId));

        BordoreauQRDTO qrDTO = new BordoreauQRDTO();
        qrDTO.setNumeroBordoreau(bordoreau.getBordoreau());
        qrDTO.setDate(bordoreau.getDate().format(DateTimeFormatter.ofPattern("yyMMdd")));
        qrDTO.setStringLivreur(bordoreau.getLivreur().getCinDriver());
        qrDTO.setCodeSecteur(bordoreau.getSecteur().getIdSecteur());
        qrDTO.setStatus(bordoreau.getStatus());

        qrDTO.setPackets(bordoreau.getPacketsBordoreau().stream().map(packet -> {
            PacketDetailDTO detail = new PacketDetailDTO();
            detail.setNumeroBL(packet.getIdPacket());
            detail.setCodeClient(packet.getClient().getCinClient());
            detail.setNbrColis(packet.getColis());
            detail.setNbrSachets(packet.getSachets());
            return detail;
        }).collect(Collectors.toList()));

        return qrDTO;
    }
    @Override
    public Bordoreau updateBordoreau1(Long id, UpdateBordoreauRequest updateRequest) {
        Bordoreau bordoreau = bordoreauRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bordoreau not found with id: " + id));

        /*try {
            bordoreau.setDate(LocalDateTime.parse(updateRequest.getDate()));
        } catch (DateTimeParseException e) {
            throw new RuntimeException("Invalid date format: " + updateRequest.getDate());
        }*/

        bordoreau.setStatus(updateRequest.getStatus());

        Set<Packet> updatedPackets = updateRequest.getPackets().stream().map(packetRequest -> {
            Packet packet = packetRepository.findById(packetRequest.getIdPacket())
                    .orElseThrow(() -> new RuntimeException("Packet not found with id: " + packetRequest.getIdPacket()));
            packet.setColis(packetRequest.getColis());
            packet.setSachets(packetRequest.getSachets());
            packet.setStatus(packetRequest.getStatus());
            return packet;
        }).collect(Collectors.toSet());

        bordoreau.setPacketsBordoreau(updatedPackets);

        // Save the Bordoreau and its Packets
        packetRepository.saveAll(updatedPackets);
        bordoreau = bordoreauRepository.save(bordoreau);


            return bordoreau;
    }


    @Override
    public void processBordoreau(Map<String, Object> bordereauData) throws Exception {
        String driverCode = (String) bordereauData.get("codeLibreur");

        // Check if the driver exists
        Optional<Driver> driver = driverRepository.findById(driverCode);
        if (!driver.isPresent()) {
            throw new Exception("Driver not found");
        }

        // Fetch the Secteur entity by idSecteur
        Long idSecteur = Long.parseLong(bordereauData.get("codeSecteur").toString());
        Secteur secteur = secteurRepository.findById(idSecteur)
                .orElseThrow(() -> new Exception("Secteur not found for idSecteur: " + idSecteur));

        // Create and save Bordereau
        Bordoreau bordoreau = new Bordoreau();
        bordoreau.setBordoreau(Long.parseLong(bordereauData.get("idBordoreau").toString()));
        bordoreau.setDate(LocalDateTime.parse(bordereauData.get("date").toString()));
        bordoreau.setLivreur(driver.get());
        bordoreau.setSecteur(secteur);

        // Save the Bordereau to the database
        bordoreau = bordoreauRepository.save(bordoreau);

        // Process packets
        List<Map<String, Object>> packetsData = (List<Map<String, Object>>) bordereauData.get("packets");
        for (Map<String, Object> packetData : packetsData) {
            String clientCode = packetData.get("cinClient").toString();

            // Check if client exists or create a new one
            Client client = clientRepository.findById(clientCode).orElseGet(() -> {
                Client newClient = new Client();
                newClient.setCinClient(clientCode);
                return clientRepository.save(newClient);
            });

            // Create and save Packet
            Packet packet = new Packet();
            packet.setIdPacket(Long.parseLong(packetData.get("idPacket").toString()));
            packet.setClient(client);
            packet.setColis(Integer.parseInt(packetData.get("colis").toString()));
            packet.setSachets(Integer.parseInt(packetData.get("sachets").toString()));
            packet.setStatus(PacketStatus.INITIALIZED);  // Assuming INITIALIZED is the default status
            packet.setBordoreau(bordoreau);

            packetRepository.save(packet);
        }
    }

    public String getAllBordoreauxAsJson() {
        try {
            // Retrieve all Bordoreau objects from the database or repository
            List<Bordoreau> bordoreaux = bordoreauRepository.findAll();

            // Serialize the Bordoreau objects to JSON array
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(bordoreaux);
        } catch (JsonProcessingException e) {
            e.printStackTrace(); // Handle the exception as needed
            return null;
        }
    }
}
