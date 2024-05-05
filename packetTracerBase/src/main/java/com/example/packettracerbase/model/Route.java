package com.example.packettracerbase.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
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

    private LocalDateTime startTime;

    @ElementCollection
    private Set<Location> waypoints;

    private LocalDateTime  endTimeEstimated;

    private Float distance;

    private Float duration;

    //@JsonIgnore
    //@JsonBackReference
    @ManyToMany(mappedBy = "routes")
    private Set<Driver> drivers;


    @OneToMany(mappedBy = "route")
    private Set<Packet> packets;
}
