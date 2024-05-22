package com.example.packettracer.model;

import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
@Entity
public class BordoreauQRDTO {
    @PrimaryKey
    private Long numeroBordoreau;
    private String date;
    private String stringLivreur;
    private Long codeSecteur;
    @TypeConverters(PacketDetailConverter.class)
    private List<PacketDetailDTO> packets;

    private PacketStatus status;

    public PacketStatus getStatus() {
        return status;
    }

    public void setStatus(PacketStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BordoreauQRDTO)) return false;
        BordoreauQRDTO that = (BordoreauQRDTO) o;
        return Objects.equals(getNumeroBordoreau(), that.getNumeroBordoreau()) && Objects.equals(getDate(), that.getDate()) && Objects.equals(getStringLivreur(), that.getStringLivreur()) && Objects.equals(getCodeSecteur(), that.getCodeSecteur()) && Objects.equals(getPackets(), that.getPackets()) && getStatus() == that.getStatus();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNumeroBordoreau(), getDate(), getStringLivreur(), getCodeSecteur(), getPackets(), getStatus());
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

    public BordoreauQRDTO(Long numeroBordoreau,PacketStatus status, String date, String stringLivreur, Long codeSecteur, List<PacketDetailDTO> packets) {
        this.numeroBordoreau = numeroBordoreau;
        this.date = date;
        this.stringLivreur = stringLivreur;
        this.codeSecteur = codeSecteur;
        this.packets = packets;
        this.status = status;
    }

    @NonNull
    @Override
    public String toString() {
        return "BordoreauQRDTO{" +
                "numeroBordoreau=" + numeroBordoreau +
                ", date='" + date + '\'' +
                ", stringLivreur='" + stringLivreur + '\'' +
                ", codeSecteur=" + codeSecteur +
                ", packets=" + packets.toString() +
                ", status=" + status +
                '}';
    }
}
