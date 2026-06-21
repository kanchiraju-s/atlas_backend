package com.atlas.atlas_backend.discussions.controller;

import com.atlas.atlas_backend.common.dto.ApiResponse;
import com.atlas.atlas_backend.discussions.dto.CreateDiscussionRequest;
import com.atlas.atlas_backend.discussions.dto.DiscussionResponse;
import com.atlas.atlas_backend.discussions.entity.Discussion;
import com.atlas.atlas_backend.discussions.repository.DiscussionRepository;
import com.atlas.atlas_backend.discussions.service.DiscussionService;
import com.atlas.atlas_backend.users.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DiscussionController {

    private final DiscussionService discussionService;
    private final DiscussionRepository discussionRepository;
    private final UserRepository userRepository;

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
            @Valid @RequestBody CreateDiscussionRequest request,
            org.springframework.security.core.Authentication authentication
    ) {
        UUID authorId = (UUID) authentication.getPrincipal();
        Discussion discussion = Discussion.builder()
                .dropId(dropId)
                .authorId(authorId)
                .content(request.getContent())
                .parentDiscussionId(request.getParentDiscussionId())
                .build();

        Discussion saved = discussionService.createDiscussion(discussion);

        String authorName = userRepository.findById(saved.getAuthorId())
                .map(u -> u.getDisplayName())
                .orElse(null);

        DiscussionResponse response = DiscussionResponse.builder()
                .id(saved.getId())
                .dropId(saved.getDropId())
                .authorId(saved.getAuthorId())
                .authorName(authorName)
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
        List<Discussion> found = discussionRepository.findTop20ByContentContainingIgnoreCase(q);

        // Batch-load authors to avoid N+1
        List<UUID> authorIds = found.stream().map(Discussion::getAuthorId).distinct().toList();
        Map<UUID, String> nameById = userRepository.findAllById(authorIds)
                .stream()
                .collect(java.util.stream.Collectors.toMap(
                        u -> u.getId(), u -> u.getDisplayName()));

        List<DiscussionResponse> results = found.stream()
                .map(d -> DiscussionResponse.builder()
                        .id(d.getId())
                        .dropId(d.getDropId())
                        .authorId(d.getAuthorId())
                        .authorName(nameById.get(d.getAuthorId()))
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
