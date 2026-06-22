package com.atlas.atlas_backend.topics.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Builder
public class TopicResponse {

    private UUID id;
    private String title;
    private String slug;
    private Instant createdAt;
    private UUID createdBy;
}
