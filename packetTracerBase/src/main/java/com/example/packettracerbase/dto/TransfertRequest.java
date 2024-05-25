package com.example.packettracerbase.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
public class TransfertRequest {
    private Long codeSecteur;
    private Long idDriver;
    private Set<Long> packets;

}
