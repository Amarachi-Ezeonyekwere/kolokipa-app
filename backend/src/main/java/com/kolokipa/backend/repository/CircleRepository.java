package com.kolokipa.backend.repository;

import com.kolokipa.backend.entity.Circle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CircleRepository extends JpaRepository<Circle, UUID> {
}