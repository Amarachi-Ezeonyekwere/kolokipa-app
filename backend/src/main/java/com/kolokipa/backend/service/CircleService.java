package com.kolokipa.backend.service;

import com.kolokipa.backend.dto.CircleCreateRequest;
import com.kolokipa.backend.dto.CircleResponse;
import com.kolokipa.backend.entity.Circle;
import com.kolokipa.backend.repository.CircleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.kolokipa.backend.exception.ResourceNotFoundException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CircleService {

    private final CircleRepository circleRepository;

    public CircleResponse createCircle(CircleCreateRequest request) {
        Circle circle = Circle.builder()
                .name(request.name())
                .contributionAmount(request.contributionAmount())
                .cycleFrequency(request.cycleFrequency())
                .terminologyProfile(request.terminologyProfile())
                .currency(request.currency())
                .timezone(request.timezone())
                .build();

        Circle saved = circleRepository.save(circle);

        return toResponse(saved);
    }

    public List<CircleResponse> getAllCircles() {
        return circleRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public CircleResponse getCircleById(UUID id) {
        Circle circle = circleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Circle not found with id: " + id));
        return toResponse(circle);
    }

    private CircleResponse toResponse(Circle circle) {
        return new CircleResponse(
                circle.getId(),
                circle.getName(),
                circle.getContributionAmount(),
                circle.getCycleFrequency(),
                circle.getTerminologyProfile(),
                circle.getCurrency(),
                circle.getTimezone(),
                circle.getCreatedAt()
        );
    }
}