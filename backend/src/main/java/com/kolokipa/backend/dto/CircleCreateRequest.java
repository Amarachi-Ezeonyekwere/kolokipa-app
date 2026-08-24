package com.kolokipa.backend.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CircleCreateRequest(

        @NotBlank(message = "Circle name is required")
        String name,

        @NotNull(message = "Contribution amount is required")
        @DecimalMin(value = "0.01", message = "Contribution amount must be greater than zero")
        BigDecimal contributionAmount,

        @NotBlank(message = "Cycle frequency is required")
        String cycleFrequency,

        @NotBlank(message = "Terminology profile is required")
        String terminologyProfile,

        @NotBlank(message = "Currency is required")
        String currency

) {}