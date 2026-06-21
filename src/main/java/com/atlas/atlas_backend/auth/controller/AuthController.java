package com.atlas.atlas_backend.auth.controller;

import com.atlas.atlas_backend.auth.dto.AuthResponse;
import com.atlas.atlas_backend.auth.dto.GoogleSignInRequest;
import com.atlas.atlas_backend.auth.dto.RefreshRequest;
import com.atlas.atlas_backend.auth.service.GoogleAuthService;
import com.atlas.atlas_backend.auth.service.JwtService;
import com.atlas.atlas_backend.common.dto.ApiResponse;
import com.atlas.atlas_backend.users.entity.User;
import com.atlas.atlas_backend.users.repository.UserRepository;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private static final int MAX_ALPHA_USERS = 500;

    private final GoogleAuthService googleAuthService;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final JdbcTemplate jdbcTemplate;

    @PostMapping("/google")
    public ApiResponse<AuthResponse> googleSignIn(@RequestBody GoogleSignInRequest req) {
        GoogleIdToken.Payload payload = googleAuthService.verify(req.getIdToken());

        String googleId = payload.getSubject();
        String email = payload.getEmail();
        String name = (String) payload.get("name");

        Optional<User> existing = userRepository.findByGoogleId(googleId);
        User user = existing.orElseGet(() -> createUser(googleId, email, name));

        return ApiResponse.<AuthResponse>builder()
                .success(true)
                .message("Authenticated")
                .data(toAuthResponse(user))
                .build();
    }

    @PostMapping("/refresh")
    public ApiResponse<AuthResponse> refresh(@RequestBody RefreshRequest req) {
        if (!jwtService.isRefreshToken(req.getRefreshToken())) {
            throw new IllegalArgumentException("Not a refresh token");
        }
        UUID userId = jwtService.validateAndExtractUserId(req.getRefreshToken());
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return ApiResponse.<AuthResponse>builder()
                .success(true)
                .message("Tokens refreshed")
                .data(toAuthResponse(user))
                .build();
    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout(@RequestBody RefreshRequest req) {
        // Stateless — client discards tokens. Future: add token blacklist.
        return ApiResponse.<Void>builder().success(true).message("Logged out").build();
    }

    private User createUser(String googleId, String email, String displayName) {
        long activeCount = userRepository.countByStatus("ACTIVE");
        String status = activeCount >= MAX_ALPHA_USERS ? "WAITLISTED" : "ACTIVE";

        Integer explorerNumber = null;
        if ("ACTIVE".equals(status)) {
            explorerNumber = jdbcTemplate.queryForObject(
                    "SELECT nextval('explorer_number_seq')", Integer.class);
        }

        String username = generateUsername(email);

        User user = User.builder()
                .googleId(googleId)
                .email(email)
                .displayName(displayName != null ? displayName : username)
                .username(username)
                .status(status)
                .explorerNumber(explorerNumber)
                .build();

        return userRepository.save(user);
    }

    private String generateUsername(String email) {
        String base = email.split("@")[0].replaceAll("[^a-zA-Z0-9_]", "").toLowerCase();
        if (base.length() > 20) base = base.substring(0, 20);
        String candidate = base;
        int suffix = 1;
        while (userRepository.existsByUsername(candidate)) {
            candidate = base + suffix++;
        }
        return candidate;
    }

    private AuthResponse toAuthResponse(User user) {
        return AuthResponse.builder()
                .accessToken(jwtService.generateAccessToken(user.getId()))
                .refreshToken(jwtService.generateRefreshToken(user.getId()))
                .userId(user.getId().toString())
                .explorerNumber(user.getExplorerNumber() != null ? user.getExplorerNumber() : 0)
                .status(user.getStatus())
                .displayName(user.getDisplayName())
                .username(user.getUsername())
                .build();
    }
}
