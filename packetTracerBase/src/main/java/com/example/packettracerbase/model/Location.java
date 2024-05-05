    package com.example.packettracerbase.model;

    import jakarta.persistence.Embeddable;
    import lombok.Data;

    @Embeddable
    @Data
    public class Location {
        protected Double latitude;
        protected Double longitude;
    }
