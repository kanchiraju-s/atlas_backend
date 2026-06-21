package com.atlas.atlas_backend.topics.controller;


import com.atlas.atlas_backend.common.dto.ApiResponse;
import com.atlas.atlas_backend.topics.dto.CreateTopicRequest;
import com.atlas.atlas_backend.topics.dto.TopicResponse;
import com.atlas.atlas_backend.topics.entity.Topic;
import com.atlas.atlas_backend.topics.service.TopicService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/topics")
@RequiredArgsConstructor
public class TopicController {

    private final TopicService topicService;

    @GetMapping("/search")
    public ApiResponse<List<TopicResponse>> searchTopics(
            @RequestParam String q
    ) {

        List<TopicResponse> response =
                topicService.searchTopics(q)
                        .stream()
                        .map(this::toResponse)
                        .toList();

        return ApiResponse.<List<TopicResponse>>builder()
                .success(true)
                .message("Topics fetched successfully")
                .data(response)
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<TopicResponse> getTopic(
            @PathVariable UUID id
    ) {

        Topic topic = topicService.getTopic(id);

        return ApiResponse.<TopicResponse>builder()
                .success(true)
                .message("Topic fetched successfully")
                .data(toResponse(topic))
                .build();
    }

    @PostMapping
    public ApiResponse<TopicResponse> createTopic(
            @Valid @RequestBody CreateTopicRequest request,
            Authentication authentication
    ) {
        UUID createdBy = (UUID) authentication.getPrincipal();

        Topic topic = Topic.builder()
                .title(request.getTitle())
                .createdBy(createdBy)
                .build();

        topic = topicService.createTopic(topic);

        return ApiResponse.<TopicResponse>builder()
                .success(true)
                .message("Topic created successfully")
                .data(toResponse(topic))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteTopic(
            @PathVariable UUID id,
            Authentication authentication
    ) {
        UUID requesterId = (UUID) authentication.getPrincipal();
        topicService.deleteTopic(id, requesterId);
        return ApiResponse.<Void>builder()
                .success(true)
                .message("Topic deleted successfully")
                .data(null)
                .build();
    }

    private TopicResponse toResponse(
            Topic topic
    ) {
        return TopicResponse.builder()
                .id(topic.getId())
                .title(topic.getTitle())
                .createdAt(topic.getCreatedAt())
                .createdBy(topic.getCreatedBy())
                .build();
    }
}