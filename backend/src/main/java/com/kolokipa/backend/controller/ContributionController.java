package com.kolokipa.backend.controller;

import com.kolokipa.backend.dto.ContributionResponse;
import com.kolokipa.backend.service.ContributionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/circles/{circleId}/cycles/{cycleId}/contributions")
@RequiredArgsConstructor
public class ContributionController {

    private final ContributionService contributionService;

    @GetMapping
    public ResponseEntity<List<ContributionResponse>> getContributions(
            @PathVariable UUID circleId,
            @PathVariable UUID cycleId) {

        return ResponseEntity.ok(contributionService.getContributionsByCycle(circleId, cycleId));
    }

    @PatchMapping("/{contributionId}/pay")
    public ResponseEntity<ContributionResponse> markAsPaid(
            @PathVariable UUID circleId,
            @PathVariable UUID cycleId,
            @PathVariable UUID contributionId) {

        return ResponseEntity.ok(
                contributionService.markAsPaid(circleId, cycleId, contributionId));
    }
}