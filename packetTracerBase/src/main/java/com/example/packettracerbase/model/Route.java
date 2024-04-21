package com.example.packettracerbase.model;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Route{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRouter;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime startTime;

    @ElementCollection
    private Set<Location> waypoints;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime  endTimeEstimated;

    private Float distance;

    private Float duration;
}
