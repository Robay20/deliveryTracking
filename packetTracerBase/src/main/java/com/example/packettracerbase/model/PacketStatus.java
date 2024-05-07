package com.example.packettracerbase.model;

public enum PacketStatus {
    INITIALIZED, // Changed from 'Initialized' to uppercase as per Java enum conventions
    IN_TRANSIT,
    TRANSMITTED,
    DONE
}
