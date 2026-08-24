package com.kolokipa.backend.controller;

import com.kolokipa.backend.dto.CircleCreateRequest;
import com.kolokipa.backend.dto.CircleResponse;
import com.kolokipa.backend.service.CircleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

import java.util.List;


@RestController
@RequestMapping("/circles")
@RequiredArgsConstructor
public class CircleController {

    private final CircleService circleService;

    @PostMapping
    public ResponseEntity<CircleResponse> createCircle(@Valid @RequestBody CircleCreateRequest request) {
        CircleResponse response = circleService.createCircle(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<CircleResponse>> getAllCircles() {
        return ResponseEntity.ok(circleService.getAllCircles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CircleResponse> getCircle(@PathVariable UUID id) {
        return ResponseEntity.ok(circleService.getCircleById(id));
    }
}