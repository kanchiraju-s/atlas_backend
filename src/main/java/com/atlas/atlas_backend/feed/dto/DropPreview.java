package com.atlas.atlas_backend.feed.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Builder
public class DropPreview {
    private UUID dropId;
    private UUID topicId;
    private String topicTitle;
    private UUID authorId;
    private String authorName;
    private String content;
    private Instant createdAt;
    private long discussionCount;
}
