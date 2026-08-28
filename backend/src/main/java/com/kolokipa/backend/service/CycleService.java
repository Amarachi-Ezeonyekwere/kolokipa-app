package com.kolokipa.backend.service;

import com.kolokipa.backend.dto.CycleCreateRequest;
import com.kolokipa.backend.dto.CycleResponse;
import com.kolokipa.backend.entity.Circle;
import com.kolokipa.backend.entity.Cycle;
import com.kolokipa.backend.entity.CycleStatus;
import com.kolokipa.backend.entity.Member;
import com.kolokipa.backend.exception.ResourceNotFoundException;
import com.kolokipa.backend.repository.CircleRepository;
import com.kolokipa.backend.repository.CycleRepository;
import com.kolokipa.backend.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CycleService {

    private final CycleRepository cycleRepository;
    private final CircleRepository circleRepository;
    private final MemberRepository memberRepository;

    public CycleResponse createCycle(UUID circleId, CycleCreateRequest request) {
        Circle circle = circleRepository.findById(circleId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Circle not found with id: " + circleId));

        Member collector = memberRepository.findById(request.collectorMemberId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Member not found with id: " + request.collectorMemberId()));

        if (!collector.getCircle().getId().equals(circleId)) {
            throw new IllegalArgumentException(
                    "Collector must be a member of this circle");
        }

        Cycle cycle = Cycle.builder()
                .circle(circle)
                .cycleNumber(request.cycleNumber())
                .collector(collector)
                .status(CycleStatus.UPCOMING)
                .startDate(Instant.now())
                .build();

        Cycle saved = cycleRepository.save(cycle);

        return toResponse(saved);
    }

    public List<CycleResponse> getCyclesByCircle(UUID circleId) {
        if (!circleRepository.existsById(circleId)) {
            throw new ResourceNotFoundException("Circle not found with id: " + circleId);
        }

        return cycleRepository.findByCircleIdOrderByCycleNumberAsc(circleId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private CycleResponse toResponse(Cycle cycle) {
        return new CycleResponse(
                cycle.getId(),
                cycle.getCircle().getId(),
                cycle.getCycleNumber(),
                cycle.getCollector().getId(),
                cycle.getCollector().getFullName(),
                cycle.getStatus().name(),
                cycle.getStartDate(),
                cycle.getEndDate()
        );
    }
}