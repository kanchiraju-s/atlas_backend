package com.atlas.atlas_backend.discussions.service;

import com.atlas.atlas_backend.discussions.dto.DiscussionResponse;
import com.atlas.atlas_backend.discussions.entity.Discussion;

import java.util.List;
import java.util.UUID;

public interface DiscussionService {

    List<DiscussionResponse> getDiscussions(UUID dropId);

    Discussion createDiscussion(Discussion discussion);
}
