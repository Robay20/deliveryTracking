package com.example.packet_tracer.models;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class Bordoreau {
    private Long bordoreau;
    private Date date;
    private String livreur;
    private String secteur;
    private String sender;
    private List<Packet> packets;

    public Bordoreau() {
        // Default constructor
    }

    public Bordoreau(Long bordoreau, Date date, String livreur, String secteur, String sender, List<Packet> packets) {
        this.bordoreau = bordoreau;
        this.date = date;
        this.livreur = livreur;
        this.secteur = secteur;
        this.sender = sender;
        this.packets = packets;
    }

    public Long getBordoreau() {
        return bordoreau;
    }

    public void setBordoreau(Long bordoreau) {
        this.bordoreau = bordoreau;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLivreur() {
        return livreur;
    }

    public void setLivreur(String livreur) {
        this.livreur = livreur;
    }

    public String getSecteur() {
        return secteur;
    }

    public void setSecteur(String secteur) {
        this.secteur = secteur;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public List<Packet> getPackets() {
        return packets;
    }

    public void setPackets(List<Packet> packets) {
        this.packets = packets;
    }
}