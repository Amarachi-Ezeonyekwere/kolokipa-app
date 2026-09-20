package com.kolokipa.backend.service;

import com.kolokipa.backend.entity.*;
import com.kolokipa.backend.repository.ContributionRepository;
import com.kolokipa.backend.repository.CycleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContributionServiceTest {

    @Mock private ContributionRepository contributionRepository;
    @Mock private CycleRepository cycleRepository;

    @InjectMocks private ContributionService contributionService;

    private Circle circle;
    private Cycle cycle;
    private Contribution contribution1;
    private Contribution contribution2;

    @BeforeEach
    void setUp() {
        circle = Circle.builder()
                .id(UUID.randomUUID())
                .contributionAmount(new BigDecimal("5000"))
                .build();

        cycle = Cycle.builder()
                .id(UUID.randomUUID())
                .circle(circle)
                .status(CycleStatus.UPCOMING)
                .build();

        Member member1 = Member.builder().id(UUID.randomUUID()).fullName("First").build();
        Member member2 = Member.builder().id(UUID.randomUUID()).fullName("Second").build();

        contribution1 = Contribution.builder()
                .id(UUID.randomUUID())
                .cycle(cycle)
                .member(member1)
                .amount(new BigDecimal("5000"))
                .status(ContributionStatus.PAID)
                .build();

        contribution2 = Contribution.builder()
                .id(UUID.randomUUID())
                .cycle(cycle)
                .member(member2)
                .amount(new BigDecimal("5000"))
                .status(ContributionStatus.PENDING)
                .build();
    }

    @Test
    void markingLastPendingContributionAsPaid_completesTheCycle() {
        when(contributionRepository.findById(contribution2.getId()))
                .thenReturn(Optional.of(contribution2));
        when(contributionRepository.save(any(Contribution.class)))
                .thenAnswer(inv -> inv.getArgument(0));
        when(contributionRepository.findByCycleId(cycle.getId()))
                .thenReturn(List.of(contribution1, contribution2));

        contributionService.markAsPaid(circle.getId(), cycle.getId(), contribution2.getId());

        ArgumentCaptor<Cycle> cycleCaptor = ArgumentCaptor.forClass(Cycle.class);
        verify(cycleRepository).save(cycleCaptor.capture());
        assertThat(cycleCaptor.getValue().getStatus()).isEqualTo(CycleStatus.COMPLETED);
        assertThat(cycleCaptor.getValue().getEndDate()).isNotNull();
    }

    @Test
    void markingOneOfMultiplePendingContributionsAsPaid_doesNotCompleteTheCycle() {
        Member member3 = Member.builder().id(UUID.randomUUID()).fullName("Third").build();
        Contribution contribution3 = Contribution.builder()
                .id(UUID.randomUUID())
                .cycle(cycle)
                .member(member3)
                .amount(new BigDecimal("5000"))
                .status(ContributionStatus.PENDING)
                .build();

        when(contributionRepository.findById(contribution2.getId()))
                .thenReturn(Optional.of(contribution2));
        when(contributionRepository.save(any(Contribution.class)))
                .thenAnswer(inv -> inv.getArgument(0));
        when(contributionRepository.findByCycleId(cycle.getId()))
                .thenReturn(List.of(contribution1, contribution2, contribution3));

        contributionService.markAsPaid(circle.getId(), cycle.getId(), contribution2.getId());

        verify(cycleRepository, never()).save(any(Cycle.class));
    }

    @Test
    void markingAlreadyPaidContribution_throwsError() {
        when(contributionRepository.findById(contribution1.getId()))
                .thenReturn(Optional.of(contribution1));

        assertThatThrownBy(() ->
                contributionService.markAsPaid(circle.getId(), cycle.getId(), contribution1.getId()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("already marked as paid");
    }
}