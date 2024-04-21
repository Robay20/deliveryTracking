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
        Sender sender = senderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sender not found with id: " + id));

        // Update sender details here
        sender.setUsername(senderDetails.getUsername());
        sender.setPassword(senderDetails.getPassword());
        sender.setActive(senderDetails.isActive());
        sender.setFirstName(senderDetails.getFirstName());
        sender.setLastName(senderDetails.getLastName());
        sender.setEmail(senderDetails.getEmail());
        sender.setDateOfBirth(senderDetails.getDateOfBirth());
        // Set other fields as needed

        return senderRepository.save(sender);
    }

    @Override
    public void deleteSender(String id) {
        if (!senderRepository.existsById(id)) {
            throw new EntityNotFoundException("Sender not found with id: " + id);
        }
        senderRepository.deleteById(id);
    }
}
