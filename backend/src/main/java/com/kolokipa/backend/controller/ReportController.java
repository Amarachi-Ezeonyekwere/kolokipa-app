package com.kolokipa.backend.controller;

import com.kolokipa.backend.dto.CircleSummaryResponse;
import com.kolokipa.backend.dto.MemberContributionResponse;
import com.kolokipa.backend.dto.MissedPaymentResponse;
import com.kolokipa.backend.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/circles/{circleId}")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/summary")
    public ResponseEntity<CircleSummaryResponse> getSummary(@PathVariable UUID circleId) {
        return ResponseEntity.ok(reportService.getCircleSummary(circleId));
    }

    @GetMapping("/members/{memberId}/contributions")
    public ResponseEntity<List<MemberContributionResponse>> getMemberHistory(
            @PathVariable UUID circleId,
            @PathVariable UUID memberId) {

        return ResponseEntity.ok(reportService.getMemberContributionHistory(circleId, memberId));
    }

    @GetMapping("/missed-payments")
    public ResponseEntity<List<MissedPaymentResponse>> getMissedPayments(@PathVariable UUID circleId) {
    return ResponseEntity.ok(reportService.getMissedPayments(circleId));
    }
}