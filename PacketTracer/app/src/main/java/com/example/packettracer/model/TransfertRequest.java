package com.example.packettracer.model;

import java.util.List;
import java.util.Set;

public class TransfertRequest {
    private Long codeSecteur;
    private Long idDriver;
    private Set<Long> packets;

    public TransfertRequest() {
    }

    public Set<Long> getPackets() {
        return packets;
    }

    public void setPackets(Set<Long> packets) {
        this.packets = packets;
    }

    public Long getCodeSecteur() {
        return codeSecteur;
    }

    public void setCodeSecteur(Long codeSecteur) {
        this.codeSecteur = codeSecteur;
    }

    public Long getIdDriver() {
        return idDriver;
    }

    public void setIdDriver(Long idDriver) {
        this.idDriver = idDriver;
    }
}
