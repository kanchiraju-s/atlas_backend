package com.atlas.atlas_backend.feed.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class DropPreview {
    private UUID dropId;
    private UUID topicId;
    private String topicTitle;
    private String content;
    private long discussionCount;
}
