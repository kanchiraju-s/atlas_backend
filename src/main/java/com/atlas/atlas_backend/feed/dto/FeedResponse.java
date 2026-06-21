package com.atlas.atlas_backend.feed.dto;

import com.atlas.atlas_backend.topics.dto.TopicResponse;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class FeedResponse {
    private List<TopicResponse> trendingTopics;
    private List<DropPreview> recentDrops;
    private List<DiscussionPreview> continueDiscussions;
}
