package com.kolokipa.backend.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record ContributionResponse(
        UUID id,
        UUID cycleId,
        UUID memberId,
        String memberName,
        BigDecimal amount,
        String status,
        Instant paidAt,
        Instant createdAt
) {}