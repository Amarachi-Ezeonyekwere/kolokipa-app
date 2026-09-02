package com.kolokipa.backend.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record CircleResponse(
        UUID id,
        String name,
        BigDecimal contributionAmount,
        String cycleFrequency,
        String terminologyProfile,
        String currency,
        String timezone,
        Instant createdAt
) {}