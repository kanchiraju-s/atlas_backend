package com.atlas.atlas_backend.auth.service;

import com.atlas.atlas_backend.users.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Service
public class JwtService {

    @Value("${atlas.jwt.secret}")
    private String jwtSecret;

    private static final long ACCESS_TOKEN_MINUTES = 15;
    private static final long REFRESH_TOKEN_DAYS = 30;

    private SecretKey signingKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(User user) {
        return Jwts.builder()
                .subject(user.getId().toString())
                .claim("type", "access")
                .claim("email", user.getEmail())
                .claim("explorerNumber", user.getExplorerNumber())
                .claim("status", user.getStatus())
                .issuedAt(new Date())
                .expiration(Date.from(Instant.now().plus(ACCESS_TOKEN_MINUTES, ChronoUnit.MINUTES)))
                .signWith(signingKey())
                .compact();
    }

    public String generateRefreshToken(User user) {
        return Jwts.builder()
                .subject(user.getId().toString())
                .claim("type", "refresh")
                .issuedAt(new Date())
                .expiration(Date.from(Instant.now().plus(REFRESH_TOKEN_DAYS, ChronoUnit.DAYS)))
                .signWith(signingKey())
                .compact();
    }

    public UUID validateAndExtractUserId(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(signingKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return UUID.fromString(claims.getSubject());
    }

    public boolean isRefreshToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(signingKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return "refresh".equals(claims.get("type", String.class));
    }
}
