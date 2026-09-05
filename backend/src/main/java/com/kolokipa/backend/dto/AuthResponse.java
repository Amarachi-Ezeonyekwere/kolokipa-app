package com.kolokipa.backend.dto;

import java.util.UUID;

public record AuthResponse(
        UUID userId,
        String fullName,
        String email,
        String token
) {}