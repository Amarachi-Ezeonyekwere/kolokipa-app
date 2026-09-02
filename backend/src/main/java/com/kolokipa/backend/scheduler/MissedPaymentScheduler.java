package com.kolokipa.backend.scheduler;

import com.kolokipa.backend.service.ContributionService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MissedPaymentScheduler {

    private final ContributionService contributionService;

    @Scheduled(cron = "0 0 * * * *")
    public void sweep() {
        contributionService.sweepAllMissedContributions();
    }
}