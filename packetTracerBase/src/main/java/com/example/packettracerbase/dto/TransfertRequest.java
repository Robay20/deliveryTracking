package com.example.packettracerbase.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
public class TransfertRequest {
    private String codeSecteur;
    private String idDriver;
    private Set<Long> packets;

}
