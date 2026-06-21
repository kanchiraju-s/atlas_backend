package com.atlas.atlas_backend.common.dto;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApiResponse<T> {

    private boolean success;
    private T data;
    private String message;
}