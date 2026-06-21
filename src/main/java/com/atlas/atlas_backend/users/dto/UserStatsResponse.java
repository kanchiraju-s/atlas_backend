package com.atlas.atlas_backend.users.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserStatsResponse {
    private long dropsShared;
    private long discussionsJoined;
    private long topicsCreated;
    private long topicsExplored;
}
