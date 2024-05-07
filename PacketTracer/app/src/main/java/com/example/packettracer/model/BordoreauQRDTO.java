package com.example.packettracer.model;

import java.util.List;
import java.util.Objects;

public class BordoreauQRDTO {
    private Long numeroBordoreau;
    private String date;
    private String stringLivreur;
    private Long codeSecteur;
    private List<PacketDetailDTO> packets;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BordoreauQRDTO)) return false;
        BordoreauQRDTO that = (BordoreauQRDTO) o;
        return Objects.equals(getNumeroBordoreau(), that.getNumeroBordoreau()) && Objects.equals(getDate(), that.getDate()) && Objects.equals(getStringLivreur(), that.getStringLivreur()) && Objects.equals(getCodeSecteur(), that.getCodeSecteur()) && Objects.equals(getPackets(), that.getPackets());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNumeroBordoreau(), getDate(), getStringLivreur(), getCodeSecteur(), getPackets());
    }

    public Long getNumeroBordoreau() {
        return numeroBordoreau;
    }

    public void setNumeroBordoreau(Long numeroBordoreau) {
        this.numeroBordoreau = numeroBordoreau;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStringLivreur() {
        return stringLivreur;
    }

    public void setStringLivreur(String stringLivreur) {
        this.stringLivreur = stringLivreur;
    }

    public Long getCodeSecteur() {
        return codeSecteur;
    }

    public void setCodeSecteur(Long codeSecteur) {
        this.codeSecteur = codeSecteur;
    }

    public List<PacketDetailDTO> getPackets() {
        return packets;
    }

    public void setPackets(List<PacketDetailDTO> packets) {
        this.packets = packets;
    }

    public BordoreauQRDTO(Long numeroBordoreau, String date, String stringLivreur, Long codeSecteur, List<PacketDetailDTO> packets) {
        this.numeroBordoreau = numeroBordoreau;
        this.date = date;
        this.stringLivreur = stringLivreur;
        this.codeSecteur = codeSecteur;
        this.packets = packets;
    }
}
