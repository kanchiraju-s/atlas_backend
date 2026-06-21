package com.atlas.atlas_backend.discussions.controller;

import com.atlas.atlas_backend.common.dto.ApiResponse;
import com.atlas.atlas_backend.discussions.dto.CreateDiscussionRequest;
import com.atlas.atlas_backend.discussions.dto.DiscussionResponse;
import com.atlas.atlas_backend.discussions.entity.Discussion;
import com.atlas.atlas_backend.discussions.repository.DiscussionRepository;
import com.atlas.atlas_backend.discussions.service.DiscussionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DiscussionController {

    private final DiscussionService discussionService;
    private final DiscussionRepository discussionRepository;

    @GetMapping("/drops/{dropId}/discussions")
    public ApiResponse<List<DiscussionResponse>> getDiscussions(@PathVariable UUID dropId) {
        return ApiResponse.<List<DiscussionResponse>>builder()
                .success(true)
                .message("Discussions fetched successfully")
                .data(discussionService.getDiscussions(dropId))
                .build();
    }

    @PostMapping("/drops/{dropId}/discussions")
    public ApiResponse<DiscussionResponse> createDiscussion(
            @PathVariable UUID dropId,
            @Valid @RequestBody CreateDiscussionRequest request
    ) {
        Discussion discussion = Discussion.builder()
                .dropId(dropId)
                .authorId(UUID.fromString("11111111-1111-1111-1111-111111111111"))
                .content(request.getContent())
                .parentDiscussionId(request.getParentDiscussionId())
                .build();

        Discussion saved = discussionService.createDiscussion(discussion);

        DiscussionResponse response = DiscussionResponse.builder()
                .id(saved.getId())
                .dropId(saved.getDropId())
                .authorId(saved.getAuthorId())
                .content(saved.getContent())
                .createdAt(saved.getCreatedAt())
                .parentDiscussionId(saved.getParentDiscussionId())
                .build();

        return ApiResponse.<DiscussionResponse>builder()
                .success(true)
                .message("Discussion created successfully")
                .data(response)
                .build();
    }

    @GetMapping("/discussions/search")
    public ApiResponse<List<DiscussionResponse>> searchDiscussions(@RequestParam String q) {
        List<DiscussionResponse> results = discussionRepository
                .findTop20ByContentContainingIgnoreCase(q)
                .stream()
                .map(d -> DiscussionResponse.builder()
                        .id(d.getId())
                        .dropId(d.getDropId())
                        .authorId(d.getAuthorId())
                        .content(d.getContent())
                        .createdAt(d.getCreatedAt())
                        .parentDiscussionId(d.getParentDiscussionId())
                        .build())
                .toList();
        return ApiResponse.<List<DiscussionResponse>>builder()
                .success(true)
                .message("Discussion search results")
                .data(results)
                .build();
    }
}
