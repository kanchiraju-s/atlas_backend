package com.atlas.atlas_backend.drops.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Builder
public class DropResponse {

    private UUID id;
    private UUID topicId;
    private UUID authorId;
    private String authorName;
    private String content;
    private Instant createdAt;
    private long discussionCount;
}
