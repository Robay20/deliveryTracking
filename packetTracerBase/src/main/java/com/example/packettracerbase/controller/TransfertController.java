package com.example.packettracerbase.controller;

import com.example.packettracerbase.dto.TransfertRequest;
import com.example.packettracerbase.model.Transfert;
import com.example.packettracerbase.repository.TransfertRepository;
import com.example.packettracerbase.service.TransfertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/transferts")
public class TransfertController {

    private final TransfertService transfertService;
    private final TransfertRepository transfertRepository;

    @Autowired
    public TransfertController(TransfertService transfertService, TransfertRepository transfertRepository) {
        this.transfertService = transfertService;
        this.transfertRepository = transfertRepository;
    }

    @GetMapping
    public ResponseEntity<List<Transfert>> getAllTransferts() {
        List<Transfert> transferts = transfertService.getAllTransferts();
        return new ResponseEntity<>(transferts, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transfert> getTransfertById(@PathVariable Long id) {
        return transfertService.getTransfertById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Transfert> createTransfert(@RequestBody Transfert transfert) {
        Transfert createdTransfert = transfertService.createTransfert(transfert);
        return new ResponseEntity<>(createdTransfert, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Transfert> updateTransfert(@PathVariable Long id, @RequestBody Transfert transfertDetails) {
        Transfert updatedTransfert = transfertService.updateTransfert(id, transfertDetails);
        return new ResponseEntity<>(updatedTransfert, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransfert(@PathVariable Long id) {
        transfertService.deleteTransfert(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/transferts")
    public ResponseEntity<String> createTransfert(@RequestBody TransfertRequest request) {

        // Create a new Transfert entity
        Transfert transfert = new Transfert();
        transfert.setIdTransfert(2L);
        transfert.setOldPerson(request.getCodeSecteur().toString());
        transfert.setNewPerson(request.getIdDriver().toString());
        transfert.setTime(LocalDateTime.now()); // Set current date and time

        // Save the Transfert entity
        transfertRepository.save(transfert);

        // Return a success response
        return ResponseEntity.status(HttpStatus.CREATED).body("Transfert created successfully");
    }
}
