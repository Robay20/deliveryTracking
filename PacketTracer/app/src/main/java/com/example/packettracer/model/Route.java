package com.example.packettracer.model;

import java.time.LocalDateTime;
import java.util.Set;

public class Route {
    private Long idRouter;
    private LocalDateTime startTime;
    private Set<Location> waypoints;
    private LocalDateTime endTimeEstimated;
    private Double distance;
    private Double duration;
    private Set<Driver> drivers;
    private Set<Packet> packets;

    // Constructors
    public Route() {
    }

    public Route(Long idRouter, LocalDateTime startTime, Set<Location> waypoints, LocalDateTime endTimeEstimated, Double distance, Double duration, Set<Driver> drivers, Set<Packet> packets) {
        this.idRouter = idRouter;
        this.startTime = startTime;
        this.waypoints = waypoints;
        this.endTimeEstimated = endTimeEstimated;
        this.distance = distance;
        this.duration = duration;
        this.drivers = drivers;
        this.packets = packets;
    }

    // Getters and Setters
    public Long getIdRouter() {
        return idRouter;
    }

    public void setIdRouter(Long idRouter) {
        this.idRouter = idRouter;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public Set<Location> getWaypoints() {
        return waypoints;
    }

    public void setWaypoints(Set<Location> waypoints) {
        this.waypoints = waypoints;
    }

    public LocalDateTime getEndTimeEstimated() {
        return endTimeEstimated;
    }

    public void setEndTimeEstimated(LocalDateTime endTimeEstimated) {
        this.endTimeEstimated = endTimeEstimated;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public Set<Driver> getDrivers() {
        return drivers;
    }

    public void setDrivers(Set<Driver> drivers) {
        this.drivers = drivers;
    }

    public Set<Packet> getPackets() {
        return packets;
    }

    public void setPackets(Set<Packet> packets) {
        this.packets = packets;
    }

    // Optional: Override toString(), equals(), and hashCode() methods if necessary
    @Override
    public String toString() {
        return "Route{" +
                "idRouter=" + idRouter +
                ", startTime=" + startTime +
                ", waypoints=" + waypoints +
                ", endTimeEstimated=" + endTimeEstimated +
                ", distance=" + distance +
                ", duration=" + duration +
                ", drivers=" + drivers +
                ", packets=" + packets +
                '}';
    }
}
