package com.example.packettracerbase.repository;

import com.example.packettracerbase.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {
    // Add custom queries if needed
}
