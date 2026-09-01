package com.kolokipa.backend.repository;

import com.kolokipa.backend.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MemberRepository extends JpaRepository<Member, UUID> {

    List<Member> findByCircleId(UUID circleId);
    List<Member> findByCircleIdOrderByJoinedAtAsc(UUID circleId);
}