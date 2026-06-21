package com.atlas.atlas_backend.topics.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTopicRequest {

    @NotBlank
    @Size(max = 120, message = "Topic title must be 120 characters or fewer")
    private String title;
}