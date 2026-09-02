package com.kolokipa.backend.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record MemberContributionResponse(
        UUID contributionId,
        Integer cycleNumber,
        BigDecimal amount,
        String status,
        Instant paidAt
) {}