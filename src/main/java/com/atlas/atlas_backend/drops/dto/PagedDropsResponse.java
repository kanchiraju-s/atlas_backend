package com.atlas.atlas_backend.drops.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PagedDropsResponse {
    private List<DropResponse> drops;
    private int page;
    private boolean hasMore;
}
