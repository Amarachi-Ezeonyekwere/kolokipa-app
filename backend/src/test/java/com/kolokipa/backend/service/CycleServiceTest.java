package com.kolokipa.backend.service;

import com.kolokipa.backend.entity.*;
import com.kolokipa.backend.repository.CircleRepository;
import com.kolokipa.backend.repository.CycleRepository;
import com.kolokipa.backend.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CycleServiceTest {

    @Mock private CycleRepository cycleRepository;
    @Mock private CircleRepository circleRepository;
    @Mock private MemberRepository memberRepository;
    @Mock private ContributionService contributionService;

    @InjectMocks private CycleService cycleService;

    private Circle circle;
    private Member member1;
    private Member member2;
    private Member member3;

    @BeforeEach
    void setUp() {
        UUID circleId = UUID.randomUUID();

        circle = Circle.builder()
                .id(circleId)
                .name("Test Circle")
                .contributionAmount(new BigDecimal("5000"))
                .cycleFrequency("MONTHLY")
                .timezone("Africa/Lagos")
                .build();

        member1 = Member.builder().id(UUID.randomUUID()).circle(circle).fullName("First").payoutPosition(1).build();
        member2 = Member.builder().id(UUID.randomUUID()).circle(circle).fullName("Second").payoutPosition(2).build();
        member3 = Member.builder().id(UUID.randomUUID()).circle(circle).fullName("Third").payoutPosition(3).build();

        when(circleRepository.findById(circleId)).thenReturn(Optional.of(circle));
    }

    @Test
    void firstCycle_picksFirstJoinedMember() {
        when(memberRepository.findByCircleIdOrderByJoinedAtAsc(circle.getId()))
                .thenReturn(List.of(member1, member2, member3));
        when(cycleRepository.findTopByCircleIdOrderByCycleNumberDesc(circle.getId()))
                .thenReturn(Optional.empty());
        when(cycleRepository.save(any(Cycle.class))).thenAnswer(inv -> inv.getArgument(0));

        var response = cycleService.createCycle(circle.getId());

        assertThat(response.cycleNumber()).isEqualTo(1);
        assertThat(response.collectorMemberId()).isEqualTo(member1.getId());
    }

    @Test
    void rotation_wrapsCorrectlyAfterFullCycleOfMembers() {
        when(memberRepository.findByCircleIdOrderByJoinedAtAsc(circle.getId()))
                .thenReturn(List.of(member1, member2, member3));

        Cycle previousCycle = Cycle.builder()
                .circle(circle)
                .cycleNumber(3)
                .status(CycleStatus.COMPLETED)
                .collector(member3)
                .build();

        when(cycleRepository.findTopByCircleIdOrderByCycleNumberDesc(circle.getId()))
                .thenReturn(Optional.of(previousCycle));
        when(cycleRepository.save(any(Cycle.class))).thenAnswer(inv -> inv.getArgument(0));

        var response = cycleService.createCycle(circle.getId());

        assertThat(response.cycleNumber()).isEqualTo(4);
        assertThat(response.collectorMemberId()).isEqualTo(member1.getId());
    }

    @Test
    void cannotStartNewCycle_whenPreviousCycleIsStillOpen() {
        when(memberRepository.findByCircleIdOrderByJoinedAtAsc(circle.getId()))
                .thenReturn(List.of(member1, member2, member3));

        Cycle openCycle = Cycle.builder()
                .circle(circle)
                .cycleNumber(1)
                .status(CycleStatus.UPCOMING)
                .collector(member1)
                .build();

        when(cycleRepository.findTopByCircleIdOrderByCycleNumberDesc(circle.getId()))
                .thenReturn(Optional.of(openCycle));

        assertThatThrownBy(() -> cycleService.createCycle(circle.getId()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("current cycle is completed");
    }

    @Test
    void cannotStartCycle_whenCircleHasNoMembers() {
        when(memberRepository.findByCircleIdOrderByJoinedAtAsc(circle.getId()))
                .thenReturn(List.of());

        assertThatThrownBy(() -> cycleService.createCycle(circle.getId()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("no members");
    }
}