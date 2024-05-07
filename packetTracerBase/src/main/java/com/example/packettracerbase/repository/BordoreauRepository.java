package com.example.packettracerbase.repository;

import com.example.packettracerbase.model.Bordoreau;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BordoreauRepository extends JpaRepository<Bordoreau, Long> {
    // Custom database queries can be added here
}
