package com.atlas.atlas_backend.feed.service;

import com.atlas.atlas_backend.discussions.repository.DiscussionRepository;
import com.atlas.atlas_backend.drops.entity.Drop;
import com.atlas.atlas_backend.drops.repository.DropRepository;
import com.atlas.atlas_backend.feed.dto.DiscussionPreview;
import com.atlas.atlas_backend.feed.dto.DropPreview;
import com.atlas.atlas_backend.feed.dto.FeedResponse;
import com.atlas.atlas_backend.topics.dto.TopicResponse;
import com.atlas.atlas_backend.topics.entity.Topic;
import com.atlas.atlas_backend.topics.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeedServiceImpl implements FeedService {

    private final TopicRepository topicRepository;
    private final DropRepository dropRepository;
    private final DiscussionRepository discussionRepository;

    @Override
    public FeedResponse getFeed() {
        List<Topic> latestTopics = topicRepository.findTop10ByOrderByCreatedAtDesc();
        Map<UUID, Topic> topicById = latestTopics.stream()
                .collect(Collectors.toMap(Topic::getId, Function.identity()));

        List<Drop> recentDrops = dropRepository.findTop10ByOrderByCreatedAtDesc();

        // Collect topic ids from drops not already in latestTopics
        List<UUID> missingTopicIds = recentDrops.stream()
                .map(Drop::getTopicId)
                .filter(id -> !topicById.containsKey(id))
                .distinct()
                .toList();

        if (!missingTopicIds.isEmpty()) {
            topicRepository.findAllById(missingTopicIds)
                    .forEach(t -> topicById.put(t.getId(), t));
        }

        List<DropPreview> recentDropPreviews = recentDrops.stream()
                .map(drop -> {
                    Topic topic = topicById.get(drop.getTopicId());
                    long discussionCount = discussionRepository.countByDropId(drop.getId());
                    return DropPreview.builder()
                            .dropId(drop.getId())
                            .topicId(drop.getTopicId())
                            .topicTitle(topic != null ? topic.getTitle() : "")
                            .content(drop.getContent())
                            .discussionCount(discussionCount)
                            .build();
                })
                .toList();

        // Drops with at least one discussion, ordered by discussion count desc, limit 10
        List<DiscussionPreview> continueDiscussions = recentDropPreviews.stream()
                .filter(p -> p.getDiscussionCount() > 0)
                .sorted(Comparator.comparingLong(DropPreview::getDiscussionCount).reversed())
                .limit(10)
                .map(p -> DiscussionPreview.builder()
                        .topicId(p.getTopicId())
                        .topicTitle(p.getTopicTitle())
                        .dropId(p.getDropId())
                        .dropContent(p.getContent())
                        .discussionCount(p.getDiscussionCount())
                        .build())
                .toList();

        List<TopicResponse> trendingTopics = latestTopics.stream()
                .map(t -> TopicResponse.builder()
                        .id(t.getId())
                        .title(t.getTitle())
                        .createdAt(t.getCreatedAt())
                        .build())
                .toList();

        return FeedResponse.builder()
                .trendingTopics(trendingTopics)
                .recentDrops(recentDropPreviews)
                .continueDiscussions(continueDiscussions)
                .build();
    }
}
