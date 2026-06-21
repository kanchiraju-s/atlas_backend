package com.atlas.atlas_backend.feedback.repository;

import com.atlas.atlas_backend.feedback.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FeedbackRepository extends JpaRepository<Feedback, UUID> {
}
