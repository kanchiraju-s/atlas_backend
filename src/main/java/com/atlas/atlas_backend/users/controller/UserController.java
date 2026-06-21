package com.atlas.atlas_backend.users.controller;

import com.atlas.atlas_backend.common.dto.ApiResponse;
import com.atlas.atlas_backend.discussions.repository.DiscussionRepository;
import com.atlas.atlas_backend.drops.dto.DropResponse;
import com.atlas.atlas_backend.drops.repository.DropRepository;
import com.atlas.atlas_backend.topics.dto.TopicResponse;
import com.atlas.atlas_backend.topics.repository.TopicRepository;
import com.atlas.atlas_backend.users.dto.UserResponse;
import com.atlas.atlas_backend.users.dto.UserStatsResponse;
import com.atlas.atlas_backend.users.entity.User;
import com.atlas.atlas_backend.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private static final UUID ME = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private final UserService userService;
    private final DropRepository dropRepository;
    private final DiscussionRepository discussionRepository;
    private final TopicRepository topicRepository;

    @GetMapping("/me")
    public ApiResponse<UserResponse> getMe() {
        return getUser(ME);
    }

    @GetMapping("/{id}")
    public ApiResponse<UserResponse> getUser(@PathVariable UUID id) {
        User user = userService.getUser(id);
        return ApiResponse.<UserResponse>builder()
                .success(true)
                .message("User fetched successfully")
                .data(toResponse(user))
                .build();
    }

    @GetMapping("/me/stats")
    public ApiResponse<UserStatsResponse> getMeStats() {
        return getUserStats(ME);
    }

    @GetMapping("/{id}/stats")
    public ApiResponse<UserStatsResponse> getUserStats(@PathVariable UUID id) {
        // Topics where user authored a drop
        Set<UUID> exploredTopicIds = dropRepository.findByAuthorIdOrderByCreatedAtDesc(id)
                .stream()
                .map(d -> d.getTopicId())
                .collect(java.util.stream.Collectors.toCollection(java.util.HashSet::new));

        // Topics where user participated via a discussion (resolved through the drop)
        List<UUID> discussionDropIds = discussionRepository.findByAuthorId(id)
                .stream()
                .map(d -> d.getDropId())
                .toList();
        if (!discussionDropIds.isEmpty()) {
            dropRepository.findAllById(discussionDropIds)
                    .forEach(drop -> exploredTopicIds.add(drop.getTopicId()));
        }

        UserStatsResponse stats = UserStatsResponse.builder()
                .dropsShared(dropRepository.countByAuthorId(id))
                .discussionsJoined(discussionRepository.countByAuthorId(id))
                .topicsCreated(topicRepository.countByCreatedBy(id))
                .topicsExplored(exploredTopicIds.size())
                .build();
        return ApiResponse.<UserStatsResponse>builder()
                .success(true)
                .message("User stats fetched successfully")
                .data(stats)
                .build();
    }

    @GetMapping("/me/drops")
    public ApiResponse<List<DropResponse>> getMeDrops() {
        return getUserDrops(ME);
    }

    @GetMapping("/{id}/drops")
    public ApiResponse<List<DropResponse>> getUserDrops(@PathVariable UUID id) {
        User author = userService.getUser(id);
        List<DropResponse> drops = dropRepository.findByAuthorIdOrderByCreatedAtDesc(id)
                .stream()
                .map(d -> DropResponse.builder()
                        .id(d.getId())
                        .topicId(d.getTopicId())
                        .authorId(d.getAuthorId())
                        .authorName(author.getDisplayName())
                        .content(d.getContent())
                        .createdAt(d.getCreatedAt())
                        .discussionCount(discussionRepository.countByDropId(d.getId()))
                        .build())
                .toList();
        return ApiResponse.<List<DropResponse>>builder()
                .success(true)
                .message("User drops fetched successfully")
                .data(drops)
                .build();
    }

    @GetMapping("/me/topics")
    public ApiResponse<List<TopicResponse>> getMeTopics() {
        return getUserTopics(ME);
    }

    @GetMapping("/{id}/topics")
    public ApiResponse<List<TopicResponse>> getUserTopics(@PathVariable UUID id) {
        List<TopicResponse> topics = topicRepository.findByCreatedByOrderByCreatedAtDesc(id)
                .stream()
                .map(t -> TopicResponse.builder()
                        .id(t.getId())
                        .title(t.getTitle())
                        .createdAt(t.getCreatedAt())
                        .build())
                .toList();
        return ApiResponse.<List<TopicResponse>>builder()
                .success(true)
                .message("User topics fetched successfully")
                .data(topics)
                .build();
    }

    private UserResponse toResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .displayName(user.getDisplayName())
                .avatarUrl(user.getAvatarUrl())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
