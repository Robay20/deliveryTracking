package com.example.packettracer.model;

import java.util.Objects;

public class Packet {
    private Long numeroBL;
    private String codeClient;
    private int nbrColis;
    private int nbrSachets;

    //String status;
    public Packet() {
    }

    public Packet(Long numeroBL, String codeClient, int nbrColis, int nbrSachets, String status) {
        this.numeroBL = numeroBL;
        this.codeClient = codeClient;
        this.nbrColis = nbrColis;
        this.nbrSachets = nbrSachets;
    }

    @Override
    public String toString() {
        return "Packet =>" +
                ", Client : " + codeClient  +
                ", Nombre de Colis :" + nbrColis +
                ", Nombre de Sachets :" + nbrSachets + '\n';
    }

    public Long getNumeroBL() {
        return numeroBL;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Packet)) return false;
        Packet packet = (Packet) o;
        return getNbrColis() == packet.getNbrColis() && getNbrSachets() == packet.getNbrSachets() && Objects.equals(getNumeroBL(), packet.getNumeroBL()) && Objects.equals(getCodeClient(), packet.getCodeClient());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNumeroBL(), getCodeClient(), getNbrColis(), getNbrSachets());
    }

    public void setNumeroBL(Long numeroBL) {
        this.numeroBL = numeroBL;
    }

    public String getCodeClient() {
        return codeClient;
    }

    public void setCodeClient(String codeClient) {
        this.codeClient = codeClient;
    }

    public int getNbrColis() {
        return nbrColis;
    }

    public void setNbrColis(int nbrColis) {
        this.nbrColis = nbrColis;
    }

    public int getNbrSachets() {
        return nbrSachets;
    }

    public void setNbrSachets(int nbrSachets) {
        this.nbrSachets = nbrSachets;
    }

}
