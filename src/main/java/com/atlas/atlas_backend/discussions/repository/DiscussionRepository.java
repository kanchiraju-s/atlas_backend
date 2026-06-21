package com.atlas.atlas_backend.discussions.repository;


import com.atlas.atlas_backend.discussions.entity.Discussion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DiscussionRepository extends JpaRepository<Discussion, UUID> {

    List<Discussion> findByDropIdOrderByCreatedAtAsc(UUID dropId);

    long countByDropId(UUID dropId);

    long countByAuthorId(UUID userId);

    List<Discussion> findTop20ByContentContainingIgnoreCase(String query);

    List<Discussion> findByAuthorId(UUID authorId);
}