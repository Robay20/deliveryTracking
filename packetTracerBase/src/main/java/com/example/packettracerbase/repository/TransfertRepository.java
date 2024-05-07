package com.example.packettracerbase.repository;

import com.example.packettracerbase.model.Transfert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransfertRepository extends JpaRepository<Transfert, Long> {
    // Additional custom queries can be added here if needed
}
