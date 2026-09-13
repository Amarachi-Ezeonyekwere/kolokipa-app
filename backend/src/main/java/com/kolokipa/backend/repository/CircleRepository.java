package com.kolokipa.backend.repository;

import com.kolokipa.backend.entity.Circle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface CircleRepository extends JpaRepository<Circle, UUID> {

    List<Circle> findByOwnerId(UUID ownerId);

    @Query(
        "SELECT DISTINCT c FROM Circle c LEFT JOIN Member m ON m.circle = c " +
        "WHERE c.ownerId = :userId OR m.userId = :userId"
    )
    List<Circle> findAccessibleByUserId(@Param("userId") UUID userId);
}