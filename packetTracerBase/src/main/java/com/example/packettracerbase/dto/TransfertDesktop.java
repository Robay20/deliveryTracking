package com.example.packettracerbase.dto;

import com.example.packettracerbase.model.Packet;
import jakarta.persistence.*;
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
