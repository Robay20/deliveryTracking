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
        Driver driver = driverRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Driver not found with id: " + id));

        // Update driver details here
        driver.setUsername(driverDetails.getUsername());
        driver.setPassword(driverDetails.getPassword());
        driver.setActive(driverDetails.isActive());
        driver.setFirstName(driverDetails.getFirstName());
        driver.setLastName(driverDetails.getLastName());
        driver.setEmail(driverDetails.getEmail());
        driver.setDateOfBirth(driverDetails.getDateOfBirth());
        driver.setLicenseNumber(driverDetails.getLicenseNumber());
        driver.setLicensePlate(driverDetails.getLicensePlate());
        driver.setBrand(driverDetails.getBrand());
        // Set other fields as needed

        return driverRepository.save(driver);
    }

    @Override
    public void deleteDriver(String id) {
        if (!driverRepository.existsById(id)) {
            throw new EntityNotFoundException("Driver not found with id: " + id);
        }
        driverRepository.deleteById(id);
    }
}
