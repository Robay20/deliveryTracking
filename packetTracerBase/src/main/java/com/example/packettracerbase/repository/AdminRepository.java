package com.example.packettracerbase.repository;

import com.example.packettracerbase.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, String> {
    // Add custom queries if needed
    Optional<Admin> findByUsernameAndPassword(String username, String password);

}