package com.example.packettracerbase.service;

import com.example.packettracerbase.dto.BordoreauQRDTO;
import com.example.packettracerbase.dto.DriverDTOMobile;
import com.example.packettracerbase.dto.PacketDetailDTO;
import com.example.packettracerbase.model.Bordoreau;
import com.example.packettracerbase.model.Driver;
import com.example.packettracerbase.repository.DriverRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DriverServiceImpl implements DriverService {

    private final DriverRepository driverRepository;

    @Autowired
    public DriverServiceImpl(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    @Override
    public List<Driver> getAllDrivers() {
        return driverRepository.findAll();
    }

    @Override
    public Optional<Driver> getDriverById(String id) {
        return driverRepository.findById(id);
    }

    @Override
    public Driver createDriver(Driver driver) {
        return driverRepository.save(driver);
    }
    @Override
    public Driver updateDriver(String id, Driver driverDetails) {
        Driver existingDriver = driverRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Driver not found with id: " + id));

        // Update inherited fields from Person
        existingDriver.setUsername(driverDetails.getUsername());
        existingDriver.setPassword(driverDetails.getPassword());
        existingDriver.setActive(driverDetails.isActive());
        existingDriver.setFirstName(driverDetails.getFirstName());
        existingDriver.setLastName(driverDetails.getLastName());
        existingDriver.setEmail(driverDetails.getEmail());
        existingDriver.setDateOfBirth(driverDetails.getDateOfBirth());

        // Update fields specific to Driver
        existingDriver.setLicenseNumber(driverDetails.getLicenseNumber());
        existingDriver.setLicensePlate(driverDetails.getLicensePlate());
        existingDriver.setBrand(driverDetails.getBrand());

        // Assuming you handle these collections outside of this update logic,
        // or you ensure these are managed within the same transactional context.
        existingDriver.setBordoreausDriver(driverDetails.getBordoreausDriver());

        return driverRepository.save(existingDriver);
    }

    @Override
    public void deleteDriver(String id) {
        if (!driverRepository.existsById(id)) {
            throw new EntityNotFoundException("Driver not found with id: " + id);
        }
        driverRepository.deleteById(id);
    }


    @Override
    public DriverDTOMobile convertToDriverDTOMoblie(String idMobileDriver) {
        Driver driver = driverRepository.findById(idMobileDriver)
                .orElseThrow(() -> new RuntimeException("Driver not found with id: " + idMobileDriver));

        DriverDTOMobile dto = new DriverDTOMobile();
        dto.setUsername(driver.getUsername());
        dto.setPassword(driver.getPassword());
        dto.setFirstName(driver.getFirstName());
        dto.setLastName(driver.getLastName());
        dto.setEmail(driver.getEmail());
        dto.setDateOfBirth(driver.getDateOfBirth().toString()); // Assuming getDateOfBirth returns a LocalDate or similar
        dto.setLicenseNumber(driver.getLicenseNumber());
        dto.setLicensePlate(driver.getLicensePlate());
        dto.setBrand(driver.getBrand());

        Set<BordoreauQRDTO> bordoreaus = new HashSet<>();
        for (Bordoreau bordoreau : driver.getBordoreausDriver()) {

            BordoreauQRDTO qrDTO = new BordoreauQRDTO();
            qrDTO.setNumeroBordoreau(bordoreau.getBordoreau());
            qrDTO.setDate(bordoreau.getDate().toString());
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

            bordoreaus.add(qrDTO);
        }
        dto.setBordoreauQRDTOS(bordoreaus);

        return dto;
    }

}
