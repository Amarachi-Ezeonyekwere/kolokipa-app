package com.kolokipa.backend.service;

import com.kolokipa.backend.dto.CircleSummaryResponse;
import com.kolokipa.backend.dto.MemberContributionResponse;
import com.kolokipa.backend.dto.MissedPaymentResponse;
import com.kolokipa.backend.entity.Contribution;
import com.kolokipa.backend.entity.ContributionStatus;
import com.kolokipa.backend.entity.CycleStatus;
import com.kolokipa.backend.entity.Member;
import com.kolokipa.backend.exception.ResourceNotFoundException;
import com.kolokipa.backend.repository.CircleRepository;
import com.kolokipa.backend.repository.ContributionRepository;
import com.kolokipa.backend.repository.CycleRepository;
import com.kolokipa.backend.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final CircleRepository circleRepository;
    private final CycleRepository cycleRepository;
    private final MemberRepository memberRepository;
    private final ContributionRepository contributionRepository;
    private final ContributionService contributionService;

    public CircleSummaryResponse getCircleSummary(UUID circleId) {
        if (!circleRepository.existsById(circleId)) {
            throw new ResourceNotFoundException("Circle not found with id: " + circleId);
        }
         
        contributionService.sweepMissedContributionsForCircle(circleId);

        int totalMembers = memberRepository.findByCircleId(circleId).size();

        long completedCycles = cycleRepository.findByCircleIdOrderByCycleNumberAsc(circleId)
                .stream()
                .filter(c -> c.getStatus() == CycleStatus.COMPLETED)
                .count();

        long upcomingCycles = cycleRepository.findByCircleIdOrderByCycleNumberAsc(circleId)
                .stream()
                .filter(c -> c.getStatus() == CycleStatus.UPCOMING)
                .count();

        List<Contribution> allContributions = contributionRepository.findByCycle_Circle_Id(circleId);

        BigDecimal totalExpected = allContributions.stream()
                .map(Contribution::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalCollected = allContributions.stream()
                .filter(c -> c.getStatus() == ContributionStatus.PAID)
                .map(Contribution::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        double completionRate = totalExpected.compareTo(BigDecimal.ZERO) == 0
                ? 0.0
                : totalCollected.divide(totalExpected, 4, java.math.RoundingMode.HALF_UP)
                        .doubleValue() * 100;

        return new CircleSummaryResponse(
                circleId,
                totalMembers,
                (int) completedCycles,
                (int) upcomingCycles,
                totalCollected,
                totalExpected,
                completionRate
        );
    }

    public List<MemberContributionResponse> getMemberContributionHistory(UUID circleId, UUID memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Member not found with id: " + memberId));

        if (!member.getCircle().getId().equals(circleId)) {
            throw new ResourceNotFoundException(
                    "Member " + memberId + " does not belong to circle " + circleId);
        }

        contributionService.sweepMissedContributionsForCircle(circleId);

        return contributionRepository
                .findByMember_IdAndCycle_Circle_IdOrderByCycle_CycleNumberAsc(memberId, circleId)
                .stream()
                .map(c -> new MemberContributionResponse(
                        c.getId(),
                        c.getCycle().getCycleNumber(),
                        c.getAmount(),
                        c.getStatus().name(),
                        c.getPaidAt()
                ))
                .toList();
    }

    public List<MissedPaymentResponse> getMissedPayments(UUID circleId) {
    if (!circleRepository.existsById(circleId)) {
        throw new ResourceNotFoundException("Circle not found with id: " + circleId);
    }
    
    contributionService.sweepMissedContributionsForCircle(circleId);

    return contributionRepository.findByCycle_Circle_IdAndStatus(circleId, ContributionStatus.MISSED)
            .stream()
            .map(c -> new MissedPaymentResponse(
                    c.getId(),
                    c.getMember().getId(),
                    c.getMember().getFullName(),
                    c.getCycle().getCycleNumber(),
                    c.getAmount(),
                    c.getCycle().getDueDate()
            ))
            .toList();
     }
}