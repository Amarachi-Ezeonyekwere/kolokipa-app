package com.kolokipa.backend.repository;

import com.kolokipa.backend.entity.Contribution;
import com.kolokipa.backend.entity.ContributionStatus;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ContributionRepository extends JpaRepository<Contribution, UUID> {

    List<Contribution> findByCycleId(UUID cycleId);
    List<Contribution> findByCycle_Circle_Id(UUID circleId);

    List<Contribution> findByMember_IdAndCycle_Circle_IdOrderByCycle_CycleNumberAsc(UUID memberId, UUID circleId);
    List<Contribution> findByStatus(ContributionStatus status);
    List<Contribution> findByCycle_Circle_IdAndStatus(UUID circleId, ContributionStatus status);
}