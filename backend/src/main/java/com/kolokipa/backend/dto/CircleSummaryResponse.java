package com.kolokipa.backend.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record CircleSummaryResponse(
        UUID circleId,
        int totalMembers,
        int completedCycles,
        int upcomingCycles,
        BigDecimal totalCollected,
        BigDecimal totalExpected,
        double completionRatePercent
) {}
