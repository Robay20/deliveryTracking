package com.example.packet_tracer.models;


import lombok.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Secteur {
    private Long idSecteur;

    private String city;

    private String idSender;
}
