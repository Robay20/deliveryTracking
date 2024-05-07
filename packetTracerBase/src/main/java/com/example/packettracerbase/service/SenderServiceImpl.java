package com.example.packettracerbase.service;

import com.example.packettracerbase.model.Sender;
import com.example.packettracerbase.repository.SenderRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SenderServiceImpl implements SenderService {

    private final SenderRepository senderRepository;

    @Autowired
    public SenderServiceImpl(SenderRepository senderRepository) {
        this.senderRepository = senderRepository;
    }

    @Override
    public List<Sender> getAllSenders() {
        return senderRepository.findAll();
    }

    @Override
    public Optional<Sender> getSenderById(String id) {
        return senderRepository.findById(id);
    }

    @Override
    public Sender createSender(Sender sender) {
        return senderRepository.save(sender);
    }
    @Override
    public Sender updateSender(String id, Sender senderDetails) {
        Sender existingSender = senderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sender not found with id: " + id));

        // Update inherited fields from Person
        existingSender.setUsername(senderDetails.getUsername());
        existingSender.setPassword(senderDetails.getPassword());
        existingSender.setActive(senderDetails.isActive());
        existingSender.setFirstName(senderDetails.getFirstName());
        existingSender.setLastName(senderDetails.getLastName());
        existingSender.setEmail(senderDetails.getEmail());
        existingSender.setDateOfBirth(senderDetails.getDateOfBirth());

        // Relationships - we handle relationships carefully
        // The relationship with Bordoreau might need to be managed differently if it involves changing the collection
        existingSender.setBordoreausSender(senderDetails.getBordoreausSender());

        // Since idSecteur is a one-to-one relationship and might not often change, we update it directly if needed
        existingSender.setSecteur(senderDetails.getSecteur());

        return senderRepository.save(existingSender);
    }

    @Override
    public void deleteSender(String id) {
        if (!senderRepository.existsById(id)) {
            throw new EntityNotFoundException("Sender not found with id: " + id);
        }
        senderRepository.deleteById(id);
    }
}
