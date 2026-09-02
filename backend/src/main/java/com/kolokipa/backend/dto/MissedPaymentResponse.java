package com.kolokipa.backend.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record MissedPaymentResponse(
        UUID contributionId,
        UUID memberId,
        String memberName,
        Integer cycleNumber,
        BigDecimal amount,
        Instant dueDate
) {}