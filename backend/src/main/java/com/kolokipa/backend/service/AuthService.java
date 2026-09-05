package com.kolokipa.backend.service;

import com.kolokipa.backend.dto.AuthResponse;
import com.kolokipa.backend.dto.LoginRequest;
import com.kolokipa.backend.dto.RegisterRequest;
import com.kolokipa.backend.entity.User;
import com.kolokipa.backend.repository.UserRepository;
import com.kolokipa.backend.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new IllegalArgumentException("An account with this email already exists");
        }

        User user = User.builder()
                .fullName(request.fullName())
                .email(request.email())
                .passwordHash(passwordEncoder.encode(request.password()))
                .build();

        User saved = userRepository.save(user);

        String token = jwtService.generateToken(saved.getId(), saved.getEmail());

        return new AuthResponse(saved.getId(), saved.getFullName(), saved.getEmail(), token);
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        String token = jwtService.generateToken(user.getId(), user.getEmail());

        return new AuthResponse(user.getId(), user.getFullName(), user.getEmail(), token);
    }
}