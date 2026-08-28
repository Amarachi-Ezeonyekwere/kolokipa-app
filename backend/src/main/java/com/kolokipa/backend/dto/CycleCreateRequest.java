package com.kolokipa.backend.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record CycleCreateRequest(

        @NotNull(message = "Cycle number is required")
        @Positive(message = "Cycle number must be positive")
        Integer cycleNumber,

        @NotNull(message = "Collector member ID is required")
        UUID collectorMemberId

) {}