package com.atlas.atlas_backend.feedback.dto;

import lombok.Getter;

import java.util.UUID;

@Getter
public class CreateFeedbackRequest {
    private UUID userId;
    private String tryingTo;
    private String wentWrong;
}
