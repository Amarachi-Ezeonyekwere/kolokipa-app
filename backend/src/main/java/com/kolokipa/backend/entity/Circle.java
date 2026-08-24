package com.kolokipa.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "circles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Circle {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(name = "contribution_amount", nullable = false)
    private BigDecimal contributionAmount;

    @Column(name = "cycle_frequency", nullable = false)
    private String cycleFrequency; // e.g. "WEEKLY", "MONTHLY"

    @Column(name = "terminology_profile", nullable = false)
    private String terminologyProfile; // e.g. "esusu", "susu", "chama"
    
    @Column(nullable = false)
    private String currency; // e.g. "NGN", "GHS", "KES", "ZAR" 

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = Instant.now();
    }
}