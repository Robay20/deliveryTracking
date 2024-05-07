package com.example.packettracerbase.service;

import com.example.packettracerbase.model.Driver;
import com.example.packettracerbase.repository.DriverRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
}
