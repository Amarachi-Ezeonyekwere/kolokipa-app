package com.kolokipa.backend.controller;

import com.kolokipa.backend.dto.CycleResponse;
import com.kolokipa.backend.service.CycleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/circles/{circleId}/cycles")
@RequiredArgsConstructor
public class CycleController {

    private final CycleService cycleService;


    @PostMapping
    public ResponseEntity<CycleResponse> createCycle(@PathVariable UUID circleId) { 
    CycleResponse response = cycleService.createCycle(circleId);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @GetMapping
    public ResponseEntity<List<CycleResponse>> getCycles(@PathVariable UUID circleId) {
        return ResponseEntity.ok(cycleService.getCyclesByCircle(circleId));
    }
}