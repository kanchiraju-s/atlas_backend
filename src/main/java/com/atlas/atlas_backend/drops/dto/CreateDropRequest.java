package com.atlas.atlas_backend.drops.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateDropRequest {

    @NotBlank
    private String content;
}