package com.example.packettracerbase.dto;

import com.example.packettracerbase.model.PacketStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class UpdateBordoreauRequest {

    @JsonProperty("numeroBordoreau")
    private Long bordoreau;

    @JsonProperty("date")
    private String date;

    @JsonProperty("stringLivreur")
    private String stringLivreur;

    @JsonProperty("codeSecteur")
    private Long codeSecteur;

    @JsonProperty("packets")
    private List<UpdatePacketRequest> packets;

    @JsonProperty("status")
    private PacketStatus status;

    @Override
    public String toString() {
        return "UpdateBordoreauRequest{" +
                "bordoreau=" + bordoreau +
                ", date='" + date + '\'' +
                ", stringLivreur='" + stringLivreur + '\'' +
                ", codeSecteur=" + codeSecteur +
                ", packets=" + packets.stream().map(UpdatePacketRequest::toString).collect(Collectors.joining(", ")) +
                ", status='" + status + '\'' +
                '}';
    }
    // Getters and Setters
    @Getter
    @Setter
    public static class UpdatePacketRequest {

        @JsonProperty("numeroBL")
        private Long idPacket;

        @JsonProperty("codeClient")
        private Long codeClient;

        @JsonProperty("nbrColis")
        private int colis;

        @JsonProperty("nbrSachets")
        private int sachets;

        @JsonProperty("status")
        private PacketStatus status;

        @Override
        public String toString() {
            return "UpdatePacketRequest{" +
                    "idPacket=" + idPacket +
                    ", codeClient=" + codeClient +
                    ", colis=" + colis +
                    ", sachets=" + sachets +
                    ", status='" + status + '\'' +
                    '}';
        }
        // Getters and Setters
    }

    // Getters and Setters for outer class
}
