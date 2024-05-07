package com.example.packettracerbase.service;

import com.example.packettracerbase.model.Bordoreau;

import java.util.List;
import java.util.Optional;

public interface BordoreauService {
    List<Bordoreau> getAllBordoreaux();
    Optional<Bordoreau> getBordoreauById(Long id);
    Bordoreau createBordoreau(Bordoreau bordoreau);
    Bordoreau updateBordoreau(Long id, Bordoreau bordoreauDetails);
    void deleteBordoreau(Long id);
}
