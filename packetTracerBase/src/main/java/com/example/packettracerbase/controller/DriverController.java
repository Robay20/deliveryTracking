package com.example.packettracerbase.controller;

import com.example.packettracerbase.controller.model.AuthenticationRequest;
import com.example.packettracerbase.model.Driver;
import com.example.packettracerbase.service.AuthenticationService;
import com.example.packettracerbase.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/drivers")
public class DriverController {

    private final DriverService driverService;
    private final AuthenticationService authenticationService;


    @Autowired
    public DriverController(DriverService driverService,AuthenticationService authenticationService) {
        this.driverService = driverService;
        this.authenticationService = authenticationService;

    }

    @GetMapping
    public ResponseEntity<List<Driver>> getAllDrivers() {
        List<Driver> drivers = driverService.getAllDrivers();
        return new ResponseEntity<>(drivers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Driver> getDriverById(@PathVariable String id) {
        Driver driver = driverService.getDriverById(id)
                .orElseThrow(() -> new RuntimeException("Driver not found with id: " + id));
        return new ResponseEntity<>(driver, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Driver> createDriver(@RequestBody Driver driver) {
        Driver createdDriver = driverService.createDriver(driver);
        return new ResponseEntity<>(createdDriver, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Driver> updateDriver(@PathVariable String id, @RequestBody Driver driverDetails) {
        Driver updatedDriver = driverService.updateDriver(id, driverDetails);
        return new ResponseEntity<>(updatedDriver, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDriver(@PathVariable String id) {
        driverService.deleteDriver(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthenticationRequest request) {
        boolean isAuthenticated = authenticationService.authenticateDriver(request.getUsername(), request.getPassword());
        if (isAuthenticated) {
            return new ResponseEntity<>("Driver login successful", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Driver login failed", HttpStatus.UNAUTHORIZED);
        }
    }
}
