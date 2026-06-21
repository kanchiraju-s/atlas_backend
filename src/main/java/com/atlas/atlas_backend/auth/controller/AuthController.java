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
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

@Slf4j
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
        log.info("[Atlas Auth] POST /auth/google received (tokenLength={})",
                req.getIdToken() != null ? req.getIdToken().length() : 0);
        GoogleIdToken.Payload payload = googleAuthService.verify(req.getIdToken());

        String googleId = payload.getSubject();
        String email = payload.getEmail();
        String name = (String) payload.get("name");
        String picture = (String) payload.get("picture");

        Optional<User> existing = userRepository.findByGoogleId(googleId);
        User user = existing.map(u -> updateUser(u, name, email, picture))
                            .orElseGet(() -> createUser(googleId, email, name, picture));

        AuthResponse resp = toAuthResponse(user);
        log.info("[Atlas Auth] Sign-in complete — userId={}, explorerNumber={}, status={}",
                user.getId(), user.getExplorerNumber(), user.getStatus());
        return ApiResponse.<AuthResponse>builder()
                .success(true)
                .message("Authenticated")
                .data(resp)
                .build();
    }

    @PostMapping("/refresh")
    public ApiResponse<AuthResponse> refresh(@RequestBody RefreshRequest req) {
        if (!jwtService.isRefreshToken(req.getRefreshToken())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid refresh token");
        }
        UUID userId = jwtService.validateAndExtractUserId(req.getRefreshToken());
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));

        return ApiResponse.<AuthResponse>builder()
                .success(true)
                .message("Tokens refreshed")
                .data(toAuthResponse(user))
                .build();
    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout(@RequestBody(required = false) RefreshRequest req) {
        return ApiResponse.<Void>builder().success(true).message("Logged out").build();
    }

    // Synchronized + SERIALIZABLE transaction prevents race conditions at user #500
    @Transactional(isolation = Isolation.SERIALIZABLE)
    protected synchronized User createUser(String googleId, String email, String displayName, String picture) {
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
                .avatarUrl(picture)
                .status(status)
                .explorerNumber(explorerNumber)
                .build();

        return userRepository.save(user);
    }

    private User updateUser(User user, String name, String email, String picture) {
        boolean changed = false;
        if (name != null && !name.equals(user.getDisplayName())) {
            user.setDisplayName(name);
            changed = true;
        }
        if (email != null && !email.equals(user.getEmail())) {
            user.setEmail(email);
            changed = true;
        }
        if (picture != null && !picture.equals(user.getAvatarUrl())) {
            user.setAvatarUrl(picture);
            changed = true;
        }
        return changed ? userRepository.save(user) : user;
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
                .accessToken(jwtService.generateAccessToken(user))
                .refreshToken(jwtService.generateRefreshToken(user))
                .userId(user.getId().toString())
                .explorerNumber(user.getExplorerNumber() != null ? user.getExplorerNumber() : 0)
                .status(user.getStatus())
                .displayName(user.getDisplayName())
                .username(user.getUsername())
                .build();
    }
}
