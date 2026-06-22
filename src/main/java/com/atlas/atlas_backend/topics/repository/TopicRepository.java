package com.atlas.atlas_backend.topics.repository;

import com.atlas.atlas_backend.topics.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TopicRepository extends JpaRepository<Topic, UUID> {

    List<Topic> findTop20ByTitleContainingIgnoreCase(String query);

    boolean existsByTitleIgnoreCase(String title);

    boolean existsBySlug(String slug);

    Optional<Topic> findBySlug(String slug);

    List<Topic> findTop10ByOrderByCreatedAtDesc();

    long countByCreatedBy(UUID userId);

    List<Topic> findByCreatedByOrderByCreatedAtDesc(UUID userId);
}
