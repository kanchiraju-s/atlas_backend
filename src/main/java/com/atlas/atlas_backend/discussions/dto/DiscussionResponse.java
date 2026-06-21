package com.atlas.atlas_backend.discussions.dto;

import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiscussionResponse {

    private UUID id;
    private UUID dropId;
    private UUID authorId;
    private String authorName;
    private String content;
    private Instant createdAt;

    private UUID parentDiscussionId;

    @Builder.Default
    private List<DiscussionResponse> replies = new ArrayList<>();
}
