package com.atlas.atlas_backend.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthResponse {
    private String accessToken;
    private String refreshToken;
    private String userId;
    private int explorerNumber;
    private String status;
    private String displayName;
    private String username;
}
