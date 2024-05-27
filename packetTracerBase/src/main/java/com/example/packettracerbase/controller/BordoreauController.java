package com.example.packettracerbase.controller;

import com.example.packettracerbase.dto.BordoreauMapper;
import com.example.packettracerbase.dto.BordoreauQRDTO;
import com.example.packettracerbase.dto.UpdateBordoreauRequest;
import com.example.packettracerbase.model.Bordoreau;
import com.example.packettracerbase.service.BordoreauService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/bordoreaux")
public class BordoreauController {

    private final BordoreauService bordoreauService;

    private BordoreauMapper bordoreauMapper;

    @Autowired
    public BordoreauController(BordoreauService bordoreauService, BordoreauMapper bordoreauMapper) {
        this.bordoreauService = bordoreauService;
        this.bordoreauMapper = bordoreauMapper;
    }

    @GetMapping
    public ResponseEntity<List<Bordoreau>> getAllBordoreaux() {
        List<Bordoreau> bordoreaux = bordoreauService.getAllBordoreaux();
        return new ResponseEntity<>(bordoreaux, HttpStatus.OK);
    }

    @PutMapping("/{id}/mobile/transit")
    public ResponseEntity<Void> updateBordoreauForMobile(@PathVariable Long id, @RequestBody String newStringLivreur) {
        bordoreauService.updateStringLivreur(id, newStringLivreur);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/Dashboard")
    public ResponseEntity<List<BordoreauQRDTO>> getAllBordoreaux1() {
        List<Bordoreau> bordoreaux = bordoreauService.getAllBordoreaux();
        List<BordoreauQRDTO> bordoreauQRDTOs = bordoreaux.stream()
                .map(bordoreauMapper::toBordoreauQRDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(bordoreauQRDTOs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bordoreau> getBordoreauById(@PathVariable Long id) {
        Bordoreau bordoreau = bordoreauService.getBordoreauById(id)
                .orElseThrow(() -> new RuntimeException("Bordoreau not found with id: " + id));
        return new ResponseEntity<>(bordoreau, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Bordoreau> createBordoreau(@RequestBody Bordoreau bordoreau) {
        Bordoreau createdBordoreau = bordoreauService.createBordoreau(bordoreau);
        return new ResponseEntity<>(createdBordoreau, HttpStatus.CREATED);
    }



    private static final Logger logger = LoggerFactory.getLogger(BordoreauController.class);

    @PutMapping("/{id}/mobile")
    public ResponseEntity<?> updateBordoreau(@PathVariable Long id, @RequestBody UpdateBordoreauRequest updateRequest) {
        logger.info("Received update request: {}", updateRequest);
        try {
            Bordoreau updatedBordoreau = bordoreauService.updateBordoreau1(id, updateRequest);
            return ResponseEntity.ok(updatedBordoreau);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bordoreau not found for id: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating Bordoreau: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Bordoreau> updateBordoreau(@PathVariable Long id, @RequestBody Bordoreau bordoreauDetails) {
        Bordoreau updatedBordoreau = bordoreauService.updateBordoreau(id, bordoreauDetails);
        return new ResponseEntity<>(updatedBordoreau, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBordoreau(@PathVariable Long id) {
        bordoreauService.deleteBordoreau(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}/qr")
    public ResponseEntity<BordoreauQRDTO> getBordoreauQR(@PathVariable Long id) {
        BordoreauQRDTO bordoreauQRDTO = bordoreauService.getBordoreauForQR(id);
        return ResponseEntity.ok(bordoreauQRDTO);
    }

    @PostMapping("/json")
    public ResponseEntity<?> addBordoreau(@RequestBody String bordereauData) {
        try {
            System.out.println(bordereauData);
            bordoreauService.processBordoreau(bordereauData);
            return ResponseEntity.status(HttpStatus.CREATED).body("Bordoreau saved successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/json")
    public ResponseEntity<String> getAllBordoreauxAsJson() {
        String bordoreauxJson = bordoreauService.getAllBordoreauxAsJson();
        if (bordoreauxJson != null) {
            return new ResponseEntity<>(bordoreauxJson, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
