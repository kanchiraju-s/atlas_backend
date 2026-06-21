package com.atlas.atlas_backend.feed.controller;

import com.atlas.atlas_backend.common.dto.ApiResponse;
import com.atlas.atlas_backend.feed.dto.FeedResponse;
import com.atlas.atlas_backend.feed.service.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FeedController {

    private final FeedService feedService;

    @GetMapping("/feed")
    public ApiResponse<FeedResponse> getFeed() {
        return ApiResponse.<FeedResponse>builder()
                .success(true)
                .message("Feed fetched successfully")
                .data(feedService.getFeed())
                .build();
    }
}
