package com.example.packettracerbase.controller;

import com.example.packettracerbase.dto.BordoreauQRDTO;
import com.example.packettracerbase.model.Bordoreau;
import com.example.packettracerbase.service.BordoreauService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bordoreaux")
public class BordoreauController {

    private final BordoreauService bordoreauService;

    @Autowired
    public BordoreauController(BordoreauService bordoreauService) {
        this.bordoreauService = bordoreauService;
    }

    @GetMapping
    public ResponseEntity<List<Bordoreau>> getAllBordoreaux() {
        List<Bordoreau> bordoreaux = bordoreauService.getAllBordoreaux();
        return new ResponseEntity<>(bordoreaux, HttpStatus.OK);
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
}
