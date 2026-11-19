package com.kolokipa.backend.controller;

import com.kolokipa.backend.service.ContributionService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Manual trigger for the missed-payment sweep, used while developing.
 * Not registered on AWS: there is no admin role yet, so any logged-in user
 * could call it. The hourly scheduler does the same work in every environment.
 */
@Profile("!aws")
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final ContributionService contributionService;

    @PostMapping("/sweep-missed-contributions")
    public ResponseEntity<Map<String, Object>> sweep() {
        int count = contributionService.sweepAllMissedContributions();
        return ResponseEntity.ok(Map.of("markedMissed", count));
    }
}
