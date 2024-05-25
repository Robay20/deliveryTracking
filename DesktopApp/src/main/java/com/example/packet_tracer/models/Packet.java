package com.example.packet_tracer.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Packet {
    private Long idPacket;
    private String clientCin;
    private int colis;
    private int sachets;
    private PacketStatus status;
    private Long bordoreau;
    private Set<Transfert> transferts;

    public Packet() {
        // Default constructor
    }

    public Packet(Long bL, String client, int colis, int sachets, PacketStatus status, Long bordoreau, Set<Transfert> transferts) {
        this.idPacket = bL;
        this.clientCin = client;
        this.colis = colis;
        this.sachets = sachets;
        this.status = status;
        this.bordoreau = bordoreau;
        this.transferts = transferts;
    }

    public Long getIdPacket() {
        return idPacket;
    }

    public void setIdPacket(Long bL) {
        this.idPacket = bL;
    }

    public String getClientCin() {
        return clientCin;
    }

    public void setClientCin(String client) {
        this.clientCin = client;
    }

    public int getColis() {
        return colis;
    }

    public void setColis(int colis) {
        this.colis = colis;
    }

    public int getSachets() {
        return sachets;
    }

    public void setSachets(int sachets) {
        this.sachets = sachets;
    }

    public PacketStatus getStatus() {
        return status;
    }

    public void setStatus(PacketStatus status) {
        this.status = status;
    }

    public Long getBordoreau() {
        return bordoreau;
    }

    public void setBordoreau(Long bordoreau) {
        this.bordoreau = bordoreau;
    }

    public Set<Transfert> getTransferts() {
        return transferts;
    }

    public void setTransferts(Set<Transfert> transferts) {
        this.transferts = transferts;
    }
}