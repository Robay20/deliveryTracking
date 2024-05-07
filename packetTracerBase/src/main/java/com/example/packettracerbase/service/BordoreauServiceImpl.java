package com.example.packettracerbase.service;

import com.example.packettracerbase.model.Bordoreau;
import com.example.packettracerbase.repository.BordoreauRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BordoreauServiceImpl implements BordoreauService {

    private final BordoreauRepository bordoreauRepository;

    @Autowired
    public BordoreauServiceImpl(BordoreauRepository bordoreauRepository) {
        this.bordoreauRepository = bordoreauRepository;
    }

    @Override
    public List<Bordoreau> getAllBordoreaux() {
        return bordoreauRepository.findAll();
    }

    @Override
    public Optional<Bordoreau> getBordoreauById(Long id) {
        return bordoreauRepository.findById(id);
    }


    @Override
    public Bordoreau createBordoreau(Bordoreau bordoreau) {
        return bordoreauRepository.save(bordoreau);
    }

    @Override
    public Bordoreau updateBordoreau(Long id, Bordoreau bordoreauDetails) {
        Bordoreau existingBordoreau = bordoreauRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Bordoreau not found with id: " + id));

        existingBordoreau.setDate(bordoreauDetails.getDate());
        existingBordoreau.setLivreur(bordoreauDetails.getLivreur());
        existingBordoreau.setSecteur(bordoreauDetails.getSecteur());
        existingBordoreau.setPacketsBordoreau(bordoreauDetails.getPacketsBordoreau());
        existingBordoreau.setSender(bordoreauDetails.getSender());

        return bordoreauRepository.save(existingBordoreau);
    }

    @Override
    public void deleteBordoreau(Long id) {
        if (!bordoreauRepository.existsById(id)) {
            throw new EntityNotFoundException("Bordoreau not found with id: " + id);
        }
        bordoreauRepository.deleteById(id);
    }
}