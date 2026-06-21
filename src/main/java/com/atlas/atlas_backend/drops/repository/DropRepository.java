package com.atlas.atlas_backend.drops.repository;

import com.atlas.atlas_backend.drops.entity.Drop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DropRepository extends JpaRepository<Drop, UUID> {

    List<Drop> findByTopicIdOrderByCreatedAtDesc(UUID topicId);

    Page<Drop> findByTopicIdOrderByCreatedAtDesc(UUID topicId, Pageable pageable);

    long countByTopicId(UUID topicId);

    List<Drop> findTop10ByOrderByCreatedAtDesc();

    List<Drop> findByAuthorIdOrderByCreatedAtDesc(UUID userId);

    long countByAuthorId(UUID userId);

    List<Drop> findTop20ByContentContainingIgnoreCase(String query);
}