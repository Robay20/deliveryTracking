package com.example.packet_tracer.models;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Admin extends Person {
    private String cinAdmin;
    private Role role;
}