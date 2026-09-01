package com.kolokipa.backend.service;

import com.kolokipa.backend.dto.ContributionResponse;
import com.kolokipa.backend.entity.*;
import com.kolokipa.backend.exception.ResourceNotFoundException;
import com.kolokipa.backend.repository.ContributionRepository;
import com.kolokipa.backend.repository.CycleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContributionService {

    private final ContributionRepository contributionRepository;
    private final CycleRepository cycleRepository;

    public void generateContributionsForCycle(Cycle cycle, List<Member> members) {
        for (Member member : members) {
            Contribution contribution = Contribution.builder()
                    .cycle(cycle)
                    .member(member)
                    .amount(cycle.getCircle().getContributionAmount())
                    .status(ContributionStatus.PENDING)
                    .build();

            contributionRepository.save(contribution);
        }
    }

    public List<ContributionResponse> getContributionsByCycle(UUID circleId, UUID cycleId) {
        Cycle cycle = cycleRepository.findById(cycleId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Cycle not found with id: " + cycleId));

        if (!cycle.getCircle().getId().equals(circleId)) {
            throw new ResourceNotFoundException(
                    "Cycle " + cycleId + " does not belong to circle " + circleId);
        }

        return contributionRepository.findByCycleId(cycleId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public ContributionResponse markAsPaid(UUID circleId, UUID cycleId, UUID contributionId) {
        Contribution contribution = contributionRepository.findById(contributionId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Contribution not found with id: " + contributionId));

        if (!contribution.getCycle().getId().equals(cycleId)
                || !contribution.getCycle().getCircle().getId().equals(circleId)) {
            throw new ResourceNotFoundException(
                    "Contribution " + contributionId + " does not belong to this cycle/circle");
        }

        if (contribution.getStatus() == ContributionStatus.PAID) {
            throw new IllegalArgumentException("Contribution is already marked as paid");
        }

        contribution.setStatus(ContributionStatus.PAID);
        contribution.setPaidAt(Instant.now());

        Contribution saved = contributionRepository.save(contribution);

        List<Contribution> allInCycle = contributionRepository.findByCycleId(cycleId);
        boolean allPaid = allInCycle.stream()
                .allMatch(c -> c.getStatus() == ContributionStatus.PAID);

        if (allPaid) {
            Cycle cycle = contribution.getCycle();
            cycle.setStatus(CycleStatus.COMPLETED);
            cycle.setEndDate(Instant.now());
            cycleRepository.save(cycle);
        }

        return toResponse(saved);
    }

    private ContributionResponse toResponse(Contribution contribution) {
        return new ContributionResponse(
                contribution.getId(),
                contribution.getCycle().getId(),
                contribution.getMember().getId(),
                contribution.getMember().getFullName(),
                contribution.getAmount(),
                contribution.getStatus().name(),
                contribution.getPaidAt(),
                contribution.getCreatedAt()
        );
    }
}