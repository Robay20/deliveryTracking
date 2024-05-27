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
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
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
    public void updateStringLivreur(Long id, String newStringLivreur) {
        Bordoreau bordoreau = bordoreauRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Bordoreau not found with id: " + id));

        Optional<Driver> driver= driverRepository.findById(newStringLivreur);
        if (driver.isPresent()){
        bordoreau.setLivreur(driver.get());
        bordoreauRepository.save(bordoreau);}
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
    public void processBordoreau(String bordereauData) {
        try {
            Bordoreau bordoreau = convertQRcodeToObjects(bordereauData);
            if (bordoreau != null) {
                bordoreauRepository.save(bordoreau);
            } else {
                System.out.println("Bordoreau conversion failed. Not saving to repository.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Bordoreau convertQRcodeToObjects(String dataString) {
        try {
            String[] parts = dataString.replaceAll("[{}]", "").split(",");
            System.out.println("Data parts: " + Arrays.toString(parts));

            // Check if the input string has at least 5 parts
            if (parts.length < 5) {
                System.out.println("------------- it has to be more than 5 parts !!");
                return null;
            }
            System.out.println("Start");

            Long idBordoreau = Long.parseLong(parts[0].trim());
            String dateStr = parts[1].trim();
            String codeLibreur = parts[2].trim();
            String codeSecteur = parts[3].trim();
            System.out.println("Start10");
            // Assuming dateStr is in the format "DDMMYY"
            int year = Integer.parseInt(dateStr.substring(4, 6)) + 2000; // Add 2000 to get the full year
            int month = Integer.parseInt(dateStr.substring(2, 4));
            int day = Integer.parseInt(dateStr.substring(0, 2));

            LocalDateTime date = LocalDateTime.of(year, month, day, 0, 0); // Assuming time is not available in the QR code

            List<Packet> packets = new ArrayList<>();
            for (int i = 4; i < parts.length; i = i + 4) {
                try {
                    Long idPacket = Long.parseLong(parts[i].trim());
                    Long codeClient = Long.parseLong(parts[i + 1].trim());
                    int colis = Integer.parseInt(parts[i + 2].trim());
                    int sachets = Integer.parseInt(parts[i + 3].trim());

                    Packet packet = new Packet();
                    packet.setIdPacket(idPacket);

                    Client client1 = new Client();
                    client1.setCinClient(codeClient.toString());
                    Client client = clientRepository.findById(codeClient.toString()).orElseGet(() -> clientRepository.save(client1));
                    System.out.println("Client found or created: " + client.getCinClient());

                    packet.setClient(client); // Assuming you have a Client constructor with codeClient
                    packet.setColis(colis);
                    packet.setSachets(sachets);
                    packet.setStatus(PacketStatus.INITIALIZED); // Adjust as needed
                    packet.setBordoreau(null); // To be set later
                    packets.add(packet);
                } catch (Exception e) {
                    System.out.println("Error parsing packet at index " + i + ": " + e.getMessage());
                    e.printStackTrace();
                }
            }
            System.out.println("Parsed packets: " + packets.size());

            Bordoreau bordoreau = new Bordoreau();
            bordoreau.setBordoreau(idBordoreau);
            bordoreau.setDate(date);

            Optional<Driver> driver = driverRepository.findById(codeLibreur);
            if (driver.isPresent()) {
                bordoreau.setLivreur(driver.get()); // Assuming you have a Driver constructor with codeLibreur
                System.out.println("Driver found: " + driver.get().getCinDriver());
            } else {
                System.out.println("Driver not found for code: " + codeLibreur);
            }

            Secteur secteur = new Secteur();
            secteur.setIdSecteur(Long.parseLong(codeSecteur));
            Secteur secteur1 = secteurRepository.findById(Long.parseLong(codeSecteur)).orElseGet(() -> secteurRepository.save(secteur));
            bordoreau.setSecteur(secteur1); // Assuming you have a Secteur constructor with codeSecteur
            System.out.println("Secteur found or created: " + secteur1.getIdSecteur());

            bordoreau.setPacketsBordoreau(new HashSet<>(packets)); // Add packets to Bordoreau

            // Set the Bordoreau object for each Packet
            for (Packet packet : packets) {
                packet.setBordoreau(bordoreau);
            }
            System.out.println("Bordoreau and packets linked successfully.");

            return bordoreau;
        } catch (Exception e) {
            System.out.println("Error in convertQRcodeToObjects: " + e.getMessage());
            e.printStackTrace();
            return null;
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
