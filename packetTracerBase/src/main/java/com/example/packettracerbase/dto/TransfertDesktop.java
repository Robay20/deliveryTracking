package com.example.packettracerbase.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class TransfertDesktop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTransfert;

    private String oldPerson;

    private String newPerson;

    private LocalDateTime time;

    private Long packet;
}
