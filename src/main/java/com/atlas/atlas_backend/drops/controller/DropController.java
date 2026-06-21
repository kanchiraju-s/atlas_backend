package com.atlas.atlas_backend.drops.controller;

import com.atlas.atlas_backend.common.dto.ApiResponse;
import com.atlas.atlas_backend.discussions.repository.DiscussionRepository;
import com.atlas.atlas_backend.drops.dto.CreateDropRequest;
import com.atlas.atlas_backend.drops.dto.DropResponse;
import com.atlas.atlas_backend.drops.dto.PagedDropsResponse;
import com.atlas.atlas_backend.drops.entity.Drop;
import com.atlas.atlas_backend.drops.repository.DropRepository;
import com.atlas.atlas_backend.drops.service.DropService;
import com.atlas.atlas_backend.users.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DropController {

    private static final int PAGE_SIZE = 10;

    private final DropService dropService;
    private final DropRepository dropRepository;
    private final DiscussionRepository discussionRepository;
    private final UserRepository userRepository;

    @GetMapping("/topics/{topicId}/drops")
    public ApiResponse<PagedDropsResponse> getDrops(
            @PathVariable UUID topicId,
            @RequestParam(defaultValue = "0") int page
    ) {
        Page<Drop> result = dropRepository.findByTopicIdOrderByCreatedAtDesc(
                topicId, PageRequest.of(page, PAGE_SIZE)
        );

        List<DropResponse> drops = result.getContent()
                .stream()
                .map(this::toEnrichedResponse)
                .toList();

        return ApiResponse.<PagedDropsResponse>builder()
                .success(true)
                .message("Drops fetched successfully")
                .data(PagedDropsResponse.builder()
                        .drops(drops)
                        .page(page)
                        .hasMore(!result.isLast())
                        .build())
                .build();
    }

    @PostMapping("/topics/{topicId}/drops")
    public ApiResponse<DropResponse> createDrop(
            @PathVariable UUID topicId,
            @Valid @RequestBody CreateDropRequest request
    ) {
        Drop drop = Drop.builder()
                .topicId(topicId)
                .content(request.getContent())
                .authorId(UUID.fromString("11111111-1111-1111-1111-111111111111"))
                .build();

        drop = dropService.createDrop(drop);

        return ApiResponse.<DropResponse>builder()
                .success(true)
                .message("Drop created successfully")
                .data(toEnrichedResponse(drop))
                .build();
    }

    @GetMapping("/drops/search")
    public ApiResponse<List<DropResponse>> searchDrops(@RequestParam String q) {
        List<DropResponse> results = dropRepository
                .findTop20ByContentContainingIgnoreCase(q)
                .stream()
                .map(this::toEnrichedResponse)
                .toList();
        return ApiResponse.<List<DropResponse>>builder()
                .success(true)
                .message("Drop search results")
                .data(results)
                .build();
    }

    private DropResponse toEnrichedResponse(Drop drop) {
        String authorName = userRepository.findById(drop.getAuthorId())
                .map(u -> u.getDisplayName())
                .orElse(null);
        long discussionCount = discussionRepository.countByDropId(drop.getId());

        return DropResponse.builder()
                .id(drop.getId())
                .topicId(drop.getTopicId())
                .authorId(drop.getAuthorId())
                .authorName(authorName)
                .content(drop.getContent())
                .createdAt(drop.getCreatedAt())
                .discussionCount(discussionCount)
                .build();
    }
}
