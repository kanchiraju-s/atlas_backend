package com.atlas.atlas_backend.discussions.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateDiscussionRequest {

    @NotBlank
    @Size(max = 1000, message = "Discussion must be 1000 characters or fewer")
    private String content;

    private UUID parentDiscussionId;
}