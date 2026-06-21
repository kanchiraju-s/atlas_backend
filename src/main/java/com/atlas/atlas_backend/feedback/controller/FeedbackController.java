package com.atlas.atlas_backend.feedback.controller;

import com.atlas.atlas_backend.common.dto.ApiResponse;
import com.atlas.atlas_backend.feedback.dto.CreateFeedbackRequest;
import com.atlas.atlas_backend.feedback.entity.Feedback;
import com.atlas.atlas_backend.feedback.repository.FeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/feedback")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackRepository feedbackRepository;

    @PostMapping
    public ApiResponse<Void> submit(@RequestBody CreateFeedbackRequest req) {
        feedbackRepository.save(
            Feedback.builder()
                .userId(req.getUserId())
                .tryingTo(req.getTryingTo())
                .wentWrong(req.getWentWrong())
                .build()
        );
        return ApiResponse.<Void>builder()
            .success(true)
            .message("Feedback received")
            .build();
    }
}
