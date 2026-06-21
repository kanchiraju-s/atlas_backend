package com.atlas.atlas_backend.auth.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Slf4j
@Service
public class GoogleAuthService {

    @Value("${atlas.google.client-id}")
    private String googleClientId;

    // Singleton: caches Google's public keys, avoids per-request HTTPS round-trip to JWKS endpoint
    private GoogleIdTokenVerifier verifier;

    @PostConstruct
    public void init() {
        verifier = new GoogleIdTokenVerifier.Builder(
                new NetHttpTransport(), GsonFactory.getDefaultInstance())
                .setAudience(Collections.singletonList(googleClientId))
                .build();
        log.info("[Atlas Auth] GoogleIdTokenVerifier ready. Expected audience: {}", googleClientId);
    }

    public GoogleIdToken.Payload verify(String idToken) {
        log.info("[Atlas Auth] Verifying Google ID token (length={})",
                idToken != null ? idToken.length() : 0);

        if (idToken == null || idToken.isBlank()) {
            log.warn("[Atlas Auth] Null or blank ID token received");
            throw new IllegalArgumentException("ID token is required");
        }

        try {
            GoogleIdToken token = verifier.verify(idToken);

            if (token == null) {
                // null means: wrong audience, expired, bad signature, or clock skew > 5 min
                log.error("[Atlas Auth] Token verification returned null. " +
                        "Check: (1) aud claim matches '{}', " +
                        "(2) token not expired, " +
                        "(3) server clock within 5 min of UTC", googleClientId);
                throw new IllegalArgumentException(
                        "Google ID token is invalid, expired, or audience mismatch");
            }

            GoogleIdToken.Payload payload = token.getPayload();
            log.info("[Atlas Auth] Token OK — sub={}, email={}, aud={}, emailVerified={}",
                    payload.getSubject(),
                    payload.getEmail(),
                    payload.getAudience(),
                    payload.getEmailVerified());

            if (Boolean.FALSE.equals(payload.getEmailVerified())) {
                log.warn("[Atlas Auth] Rejecting unverified Google email for sub={}",
                        payload.getSubject());
                throw new IllegalArgumentException("Google account email is not verified");
            }

            return payload;

        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            log.error("[Atlas Auth] Unexpected verification error: {}", e.getMessage(), e);
            throw new IllegalArgumentException("Google token verification failed: " + e.getMessage(), e);
        }
    }
}
