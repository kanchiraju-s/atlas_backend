package com.atlas.atlas_backend.feed.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class DiscussionPreview {
    private UUID topicId;
    private String topicTitle;
    private UUID dropId;
    private String dropContent;
    private long discussionCount;
}
