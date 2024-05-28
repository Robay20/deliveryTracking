package com.example.packettracerbase.repository;

import com.example.packettracerbase.model.Bordoreau;
import com.example.packettracerbase.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BordoreauRepository extends JpaRepository<Bordoreau, Long> {
    // Custom database queries can be added here
    List<Bordoreau> findByLivreur(Driver livreur);

}
