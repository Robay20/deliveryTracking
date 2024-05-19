package com.example.packet_tracer.models;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest {
    private String username;
    private String password;
}