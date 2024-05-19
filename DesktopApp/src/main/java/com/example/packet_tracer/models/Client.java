package com.example.packet_tracer.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Client extends Person {
    private String cinClient;
    private Role role;
    private Location location;
    private Set<Packet> packets;
}