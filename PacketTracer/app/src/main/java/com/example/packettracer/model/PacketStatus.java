package com.example.packettracer.model;

public enum PacketStatus {
    INITIALIZED, // Changed from 'Initialized' to uppercase as per Java enum conventions
    IN_TRANSIT,
    TRANSMITTED,
    DONE;
    public static PacketStatus fromString(String statusStr) {
        for (PacketStatus status : PacketStatus.values()) {
            if (status.name().equalsIgnoreCase(statusStr)) {
                return status;
            }
        }
        return null;  // or throw IllegalArgumentException
    }

    @Override
    public String toString() {
        switch (this) {
            case INITIALIZED:
                return "Initialized";
            case IN_TRANSIT:
                return "In Transit";
            case TRANSMITTED:
                return "Transmitted";
            case DONE:
                return "Done";
            default:
                return super.toString();
        }
    }

}