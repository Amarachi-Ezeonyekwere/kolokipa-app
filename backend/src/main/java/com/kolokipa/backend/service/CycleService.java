package com.kolokipa.backend.service;

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
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CycleService {

    private final CycleRepository cycleRepository;
    private final CircleRepository circleRepository;
    private final ContributionService contributionService;
    private final MemberRepository memberRepository;

    public CycleResponse createCycle(UUID circleId) {
    Circle circle = circleRepository.findById(circleId)
            .orElseThrow(() -> new ResourceNotFoundException(
                    "Circle not found with id: " + circleId));

    List<Member> members = memberRepository.findByCircleIdOrderByJoinedAtAsc(circleId);

    if (members.isEmpty()) {
        throw new IllegalArgumentException("Cannot start a cycle with no members");
    }

    boolean needsPositionAssignment = members.stream()
            .anyMatch(m -> m.getPayoutPosition() == null);

    if (needsPositionAssignment) {
        for (int i = 0; i < members.size(); i++) {
            members.get(i).setPayoutPosition(i + 1);
        }
        memberRepository.saveAll(members);
    }

    Optional<Cycle> latestCycle = cycleRepository.findTopByCircleIdOrderByCycleNumberDesc(circleId);

    if (latestCycle.isPresent() && latestCycle.get().getStatus() != CycleStatus.COMPLETED) {
        throw new IllegalArgumentException(
                "Cannot start a new cycle until the current cycle is completed");
    }

    int nextCycleNumber = latestCycle.map(c -> c.getCycleNumber() + 1).orElse(1);
    int expectedPosition = ((nextCycleNumber - 1) % members.size()) + 1;

    Member collector = members.stream()
            .filter(m -> expectedPosition == m.getPayoutPosition())
            .findFirst()
            .orElseThrow(() -> new IllegalStateException(
                    "No member found for payout position " + expectedPosition));

    Cycle cycle = Cycle.builder()
            .circle(circle)
            .cycleNumber(nextCycleNumber)
            .collector(collector)
            .status(CycleStatus.UPCOMING)
            .startDate(Instant.now())
            .build();

    Cycle saved = cycleRepository.save(cycle);

    contributionService.generateContributionsForCycle(saved, members);

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