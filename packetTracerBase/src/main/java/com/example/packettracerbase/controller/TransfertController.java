package com.example.packettracerbase.controller;

import com.example.packettracerbase.model.Transfert;
import com.example.packettracerbase.service.TransfertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transferts")
public class TransfertController {

    private final TransfertService transfertService;

    @Autowired
    public TransfertController(TransfertService transfertService) {
        this.transfertService = transfertService;
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
}
