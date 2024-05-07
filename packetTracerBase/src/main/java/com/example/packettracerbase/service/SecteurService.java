package com.example.packettracerbase.service;

import com.example.packettracerbase.model.Secteur;

import java.util.List;
import java.util.Optional;

public interface SecteurService {
    List<Secteur> getAllSecteurs();
    Optional<Secteur> getSecteurById(Long id);
    Secteur createSecteur(Secteur secteur);
    Secteur updateSecteur(Long id, Secteur secteurDetails);
    void deleteSecteur(Long id);
}
