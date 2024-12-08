package com.example.packettracerbase.service;

import com.example.packettracerbase.dto.BordoreauQRDTO;
import com.example.packettracerbase.dto.UpdateBordoreauRequest;
import com.example.packettracerbase.model.Bordoreau;
import com.example.packettracerbase.model.Driver;
import com.example.packettracerbase.repository.BordoreauRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BordoreauServiceImpl implements BordoreauService {
    private static final String BORDOREAU_NOT_FOUND = "Bordoreau not found with id: ";

    private final BordoreauRepository bordoreauRepository;
    private final BordoreauHelper bordoreauHelper;
    private final DriverHelper driverHelper;

    @Autowired
    public BordoreauServiceImpl(BordoreauRepository bordoreauRepository,
                                BordoreauHelper bordoreauHelper,
                                DriverHelper driverHelper) {
        this.bordoreauRepository = bordoreauRepository;
        this.bordoreauHelper = bordoreauHelper;
        this.driverHelper = driverHelper;
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
    public void updateStringLivreur(Long id, String newStringLivreur) {
        Driver driver = driverHelper.findById(newStringLivreur);
        Bordoreau bordoreau = bordoreauRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(BORDOREAU_NOT_FOUND + id));
        bordoreau.setLivreur(driver);
        bordoreauRepository.save(bordoreau);
    }

    @Override
    public Bordoreau updateBordoreau(Long id, Bordoreau bordoreauDetails) {
        return bordoreauHelper.updateBordoreauFields(id, bordoreauDetails);
    }

    @Override
    public void deleteBordoreau(Long id) {
        if (!bordoreauRepository.existsById(id)) {
            throw new EntityNotFoundException(BORDOREAU_NOT_FOUND + id);
        }
        bordoreauRepository.deleteById(id);
    }

    @Override
    public BordoreauQRDTO getBordoreauForQR(Long bordoreauId) {
        return bordoreauHelper.generateBordoreauQR(bordoreauId);
    }

    @Override
    public Bordoreau updateBordoreau1(Long id, UpdateBordoreauRequest updateRequest) {
        return bordoreauHelper.updateBordoreauWithPackets(id, updateRequest);
    }

    @Override
    public void processBordoreau(String bordereauData) {
        bordoreauHelper.processQRData(bordereauData);
    }

    @Override
    public Bordoreau updateLivreur(Long bordereauId, String newDriverId) {
        Driver driver = driverHelper.findById(newDriverId);
        Bordoreau bordoreau = bordoreauRepository.findById(bordereauId)
                .orElseThrow(() -> new EntityNotFoundException(BORDOREAU_NOT_FOUND + bordereauId));
        bordoreau.setLivreur(driver);
        return bordoreauRepository.save(bordoreau);
    }

    @Override
    public String getAllBordoreauxAsJson() {
        return bordoreauHelper.serializeBordereauxToJson();
    }

    @Override
    public List<Bordoreau> getBordereauxByDriver(Driver driver) {
        return bordoreauRepository.findByLivreur(driver);
    }
}
