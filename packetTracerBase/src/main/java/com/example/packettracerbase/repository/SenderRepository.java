package com.example.packettracerbase.repository;

import com.example.packettracerbase.model.Sender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SenderRepository extends JpaRepository<Sender, String> {
    // Add custom queries if needed
    Optional<Sender> findByUsernameAndPassword(String username, String password);

}
