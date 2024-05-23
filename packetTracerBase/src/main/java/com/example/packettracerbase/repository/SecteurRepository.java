package com.example.packettracerbase.repository;

import com.example.packettracerbase.model.Secteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecteurRepository extends JpaRepository<Secteur, Long> {
    // Additional custom queries can be added here if needed
}
