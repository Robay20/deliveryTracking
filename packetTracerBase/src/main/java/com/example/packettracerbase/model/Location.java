    package com.example.packettracerbase.model;

    import jakarta.persistence.Embeddable;
    import lombok.Data;
    import lombok.Getter;
    import lombok.Setter;

    @Embeddable
    @Data
    public class Location {
        protected Double latitude;
        protected Double longitude;
    }
