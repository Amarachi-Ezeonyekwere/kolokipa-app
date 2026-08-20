package com.kolokipa.backend.controller;

import com.kolokipa.backend.dto.MemberCreateRequest;
import com.kolokipa.backend.dto.MemberResponse;
import com.kolokipa.backend.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/circles/{circleId}/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<MemberResponse> addMember(
            @PathVariable UUID circleId,
            @Valid @RequestBody MemberCreateRequest request) {

        MemberResponse response = memberService.addMemberToCircle(circleId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<MemberResponse>> getMembers(@PathVariable UUID circleId) {
        return ResponseEntity.ok(memberService.getMembersByCircle(circleId));
    }
}