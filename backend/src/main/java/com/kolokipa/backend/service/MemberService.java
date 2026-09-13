package com.kolokipa.backend.service;

import com.kolokipa.backend.dto.MemberCreateRequest;
import com.kolokipa.backend.dto.MemberResponse;
import com.kolokipa.backend.entity.Circle;
import com.kolokipa.backend.entity.Member;
import com.kolokipa.backend.exception.ResourceNotFoundException;
import com.kolokipa.backend.repository.CircleRepository;
import com.kolokipa.backend.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import com.kolokipa.backend.entity.User;
import com.kolokipa.backend.repository.UserRepository;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final CircleRepository circleRepository;
    private final UserRepository userRepository;

    public MemberResponse addMemberToCircle(UUID circleId, MemberCreateRequest request) {
        Circle circle = circleRepository.findById(circleId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Circle not found with id: " + circleId));

        Optional<User> existingUser = userRepository.findByEmail(request.email());
        Member member = Member.builder()
                .circle(circle)
                .fullName(request.fullName())
                .email(request.email())
                .userId(existingUser.map(User::getId).orElse(null))
                .build();

        Member saved = memberRepository.save(member);

        return toResponse(saved);
    }

    public List<MemberResponse> getMembersByCircle(UUID circleId) {
        // Confirm the circle exists before querying its members —
        // otherwise an invalid circleId would silently return an empty list
        // instead of telling the caller the circle itself doesn't exist.
        if (!circleRepository.existsById(circleId)) {
            throw new ResourceNotFoundException("Circle not found with id: " + circleId);
        }

        return memberRepository.findByCircleId(circleId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private MemberResponse toResponse(Member member) {
        return new MemberResponse(
                member.getId(),
                member.getCircle().getId(),
                member.getFullName(),
                member.getEmail(),
                member.getPayoutPosition(),
                member.getJoinedAt()
        );
    }
    public void linkExistingMembersToUser(String email, UUID userId) {
    List<Member> unlinkedMembers = memberRepository.findByEmailAndUserIdIsNull(email);
    for (Member member : unlinkedMembers) {
        member.setUserId(userId);
    }
    memberRepository.saveAll(unlinkedMembers);
   }
}
