package com.example.packettracer.model;

import androidx.annotation.NonNull;

import java.util.Objects;

public class PacketDetailDTO {
    private Long numeroBL;
    private String codeClient;
    private int nbrColis;
    private int nbrSachets;

    private PacketStatus status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PacketDetailDTO)) return false;
        PacketDetailDTO that = (PacketDetailDTO) o;
        return getNbrColis() == that.getNbrColis() && getNbrSachets() == that.getNbrSachets() && Objects.equals(getNumeroBL(), that.getNumeroBL()) && Objects.equals(getCodeClient(), that.getCodeClient());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNumeroBL(), getCodeClient(), getNbrColis(), getNbrSachets());
    }

    public PacketDetailDTO(Long numeroBL, String codeClient, int nbrColis, int nbrSachets,PacketStatus status) {
        this.numeroBL = numeroBL;
        this.codeClient = codeClient;
        this.nbrColis = nbrColis;
        this.nbrSachets = nbrSachets;
        this.status=status;
    }

    public PacketStatus getStatus() {
        return status;
    }

    public void setStatus(PacketStatus status) {
        this.status = status;
    }

    public Long getNumeroBL() {
        return numeroBL;
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

    @NonNull
    @Override
    public String toString() {
        return "PacketDetailDTO{" +
                "numeroBL=" + numeroBL +
                ", codeClient='" + codeClient + '\'' +
                ", nbrColis=" + nbrColis +
                ", nbrSachets=" + nbrSachets +
                '}';
    }
}