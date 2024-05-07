package com.example.packettracerbase.service;

import com.example.packettracerbase.model.Transfert;
import com.example.packettracerbase.repository.TransfertRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransfertServiceImpl implements TransfertService {

    private final TransfertRepository transfertRepository;

    @Autowired
    public TransfertServiceImpl(TransfertRepository transfertRepository) {
        this.transfertRepository = transfertRepository;
    }

    @Override
    public List<Transfert> getAllTransferts() {
        return transfertRepository.findAll();
    }

    @Override
    public Optional<Transfert> getTransfertById(Long id) {
        return transfertRepository.findById(id);
    }

    @Override
    public Transfert createTransfert(Transfert transfert) {
        return transfertRepository.save(transfert);
    }

    @Override
    public Transfert updateTransfert(Long id, Transfert transfertDetails) {
        Transfert existingTransfert = transfertRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transfert not found with id: " + id));

        existingTransfert.setOldPerson(transfertDetails.getOldPerson());
        existingTransfert.setNewPerson(transfertDetails.getNewPerson());
        existingTransfert.setTime(transfertDetails.getTime());
        existingTransfert.setPacketTransfert(transfertDetails.getPacketTransfert());

        return transfertRepository.save(existingTransfert);
    }

    @Override
    public void deleteTransfert(Long id) {
        if (!transfertRepository.existsById(id)) {
            throw new EntityNotFoundException("Transfert not found with id: " + id);
        }
        transfertRepository.deleteById(id);
    }
}
