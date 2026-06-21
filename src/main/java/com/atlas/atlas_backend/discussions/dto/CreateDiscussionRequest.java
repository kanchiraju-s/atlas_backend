package com.atlas.atlas_backend.discussions.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateDiscussionRequest {

    @NotBlank
    private String content;

    private UUID parentDiscussionId;
}