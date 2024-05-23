package com.example.packettracerbase.service;

import com.example.packettracerbase.model.Secteur;
import com.example.packettracerbase.repository.SecteurRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SecteurServiceImpl implements SecteurService {

    private final SecteurRepository secteurRepository;

    @Autowired
    public SecteurServiceImpl(SecteurRepository secteurRepository) {
        this.secteurRepository = secteurRepository;
    }

    @Override
    public List<Secteur> getAllSecteurs() {
        return secteurRepository.findAll();
    }

    @Override
    public Optional<Secteur> getSecteurById(Long id) {
        return secteurRepository.findById(id);
    }

    @Override
    public Secteur createSecteur(Secteur secteur) {
        return secteurRepository.save(secteur);
    }

    @Override
    public Secteur updateSecteur(Long id, Secteur secteurDetails) {
        Secteur existingSecteur = secteurRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Secteur not found with id: " + id));
        existingSecteur.setCity(secteurDetails.getCity());
        existingSecteur.setIdSender(secteurDetails.getIdSender());
        return secteurRepository.save(existingSecteur);
    }

    @Override
    public void deleteSecteur(Long id) {
        if (!secteurRepository.existsById(id)) {
            throw new EntityNotFoundException("Secteur not found with id: " + id);
        }
        secteurRepository.deleteById(id);
    }
}
