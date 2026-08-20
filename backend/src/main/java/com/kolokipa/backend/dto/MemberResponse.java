package com.kolokipa.backend.dto;

import java.time.Instant;
import java.util.UUID;

public record MemberResponse(
        UUID id,
        UUID circleId,
        String fullName,
        String email,
        Integer payoutPosition,
        Instant joinedAt
) {}