package com.kolokipa.backend.dto;

import java.time.Instant;
import java.util.UUID;

public record CycleResponse(
        UUID id,
        UUID circleId,
        Integer cycleNumber,
        UUID collectorMemberId,
        String collectorName,
        String status,
        Instant startDate,
        Instant endDate
) {}