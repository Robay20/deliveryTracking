package com.example.packettracerbase.repository;

import com.example.packettracerbase.model.Packet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PacketRepository extends JpaRepository<Packet, Long> {
    // Add custom queries if needed
}
