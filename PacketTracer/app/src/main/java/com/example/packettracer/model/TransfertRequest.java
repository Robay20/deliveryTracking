package com.example.packettracer.model;

import java.util.List;
import java.util.Set;

public class TransfertRequest {
    private String codeSecteur;
    private String idDriver;
    private Set<Long> packets;

    public TransfertRequest() {
    }

    public Set<Long> getPackets() {
        return packets;
    }

    public void setPackets(Set<Long> packets) {
        this.packets = packets;
    }

    public String getCodeSecteur() {
        return codeSecteur;
    }

    public void setCodeSecteur(String codeSecteur) {
        this.codeSecteur = codeSecteur;
    }

    public String getIdDriver() {
        return idDriver;
    }

    public void setIdDriver(String idDriver) {
        this.idDriver = idDriver;
    }
}
