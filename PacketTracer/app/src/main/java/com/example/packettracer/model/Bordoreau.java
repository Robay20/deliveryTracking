package com.example.packettracer.model;

import java.util.List;
import java.util.Objects;

public class Bordoreau {
    Long numeroBordoreau;
    String date;
    String stringLivreur;
    Long codeSecteur;
    List<Packet> packets;
    String status;

    public Bordoreau() {
    }

    public Bordoreau(Long numeroBordoreau, String date, String stringLivreur, Long codeSecteur, List<Packet> packets, String status) {
        this.numeroBordoreau = numeroBordoreau;
        this.date = date;
        this.stringLivreur = stringLivreur;
        this.codeSecteur = codeSecteur;
        this.packets = packets;
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bordoreau)) return false;
        Bordoreau bordoreau = (Bordoreau) o;
        return Objects.equals(getNumeroBordoreau(), bordoreau.getNumeroBordoreau()) && Objects.equals(getDate(), bordoreau.getDate()) && Objects.equals(getStringLivreur(), bordoreau.getStringLivreur()) && Objects.equals(getCodeSecteur(), bordoreau.getCodeSecteur()) && Objects.equals(getPackets(), bordoreau.getPackets()) && Objects.equals(getStatus(), bordoreau.getStatus());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNumeroBordoreau(), getDate(), getStringLivreur(), getCodeSecteur(), getPackets(), getStatus());
    }

    @Override
    public String toString() {
        return "Bordoreau{" +
                "numeroBordoreau=" + numeroBordoreau +
                ", date='" + date + '\'' +
                ", stringLivreur='" + stringLivreur + '\'' +
                ", codeSecteur=" + codeSecteur +
                ", packets=" + packets.toString() +
                ", status='" + status + '\'' +
                '}';
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

    public List<Packet> getPackets() {
        return packets;
    }

    public void setPackets(List<Packet> packets) {
        this.packets = packets;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}