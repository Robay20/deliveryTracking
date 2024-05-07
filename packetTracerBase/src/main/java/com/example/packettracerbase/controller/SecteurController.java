package com.example.packettracerbase.controller;

import com.example.packettracerbase.model.Secteur;
import com.example.packettracerbase.service.SecteurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/secteurs")
public class SecteurController {

    private final SecteurService secteurService;

    @Autowired
    public SecteurController(SecteurService secteurService) {
        this.secteurService = secteurService;
    }

    @GetMapping
    public ResponseEntity<List<Secteur>> getAllSecteurs() {
        List<Secteur> secteurs = secteurService.getAllSecteurs();
        return new ResponseEntity<>(secteurs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Secteur> getSecteurById(@PathVariable Long id) {
        return secteurService.getSecteurById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Secteur> createSecteur(@RequestBody Secteur secteur) {
        Secteur createdSecteur = secteurService.createSecteur(secteur);
        return new ResponseEntity<>(createdSecteur, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Secteur> updateSecteur(@PathVariable Long id, @RequestBody Secteur secteurDetails) {
        Secteur updatedSecteur = secteurService.updateSecteur(id, secteurDetails);
        return new ResponseEntity<>(updatedSecteur, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSecteur(@PathVariable Long id) {
        secteurService.deleteSecteur(id);
        return ResponseEntity.ok().build();
    }
}
