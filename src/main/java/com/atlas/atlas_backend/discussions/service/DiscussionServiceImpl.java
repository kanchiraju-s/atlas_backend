package com.atlas.atlas_backend.discussions.service;

import com.atlas.atlas_backend.discussions.dto.DiscussionResponse;
import com.atlas.atlas_backend.discussions.entity.Discussion;
import com.atlas.atlas_backend.discussions.repository.DiscussionRepository;
import com.atlas.atlas_backend.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DiscussionServiceImpl implements DiscussionService {

    private final DiscussionRepository discussionRepository;
    private final UserRepository userRepository;

    @Override
    public List<DiscussionResponse> getDiscussions(UUID dropId) {
        List<Discussion> all = discussionRepository.findByDropIdOrderByCreatedAtAsc(dropId);

        // Batch-load all authors to avoid N+1
        List<UUID> authorIds = all.stream().map(Discussion::getAuthorId).distinct().toList();
        Map<UUID, String> nameById = userRepository.findAllById(authorIds).stream()
                .collect(Collectors.toMap(u -> u.getId(), u -> u.getDisplayName()));

        Map<UUID, DiscussionResponse> byId = new LinkedHashMap<>();

        for (Discussion d : all) {
            String authorName = nameById.get(d.getAuthorId());

            DiscussionResponse dto = DiscussionResponse.builder()
                    .id(d.getId())
                    .dropId(d.getDropId())
                    .authorId(d.getAuthorId())
                    .authorName(authorName)
                    .content(d.getContent())
                    .createdAt(d.getCreatedAt())
                    .parentDiscussionId(d.getParentDiscussionId())
                    .build();

            byId.put(d.getId(), dto);
        }

        List<DiscussionResponse> topLevel = new ArrayList<>();

        for (DiscussionResponse dto : byId.values()) {
            if (dto.getParentDiscussionId() == null) {
                topLevel.add(dto);
            } else {
                DiscussionResponse parent = byId.get(dto.getParentDiscussionId());
                if (parent != null) {
                    parent.getReplies().add(dto);
                }
            }
        }

        return topLevel;
    }

    @Override
    public Discussion createDiscussion(Discussion discussion) {
        return discussionRepository.save(discussion);
    }
}
