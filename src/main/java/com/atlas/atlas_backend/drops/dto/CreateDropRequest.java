package com.atlas.atlas_backend.drops.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateDropRequest {

    @NotBlank
    @Size(max = 2000, message = "Drop must be 2000 characters or fewer")
    private String content;
}