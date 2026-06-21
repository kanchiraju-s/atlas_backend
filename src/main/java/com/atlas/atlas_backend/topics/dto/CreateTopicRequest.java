package com.atlas.atlas_backend.topics.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTopicRequest {

    @NotBlank
    private String title;
}