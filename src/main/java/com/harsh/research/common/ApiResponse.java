package com.harsh.research.common;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiResponse<T>(
        boolean success,
        T data,
        ApiError error,
        String traceId,
        Instant timeStamp
){

    public static <T> ApiResponse<T> Ok(T data) {
        return new ApiResponse<>(
                true,
                data,
                null,
                null,
                Instant.now()
        );

    }

    public static <T> ApiResponse<T> Ok(T data, String traceId){
        return new ApiResponse<>(
                true,
                data,
                null,
                traceId,
                Instant.now()
        );
    }

    public static ApiResponse<Void> error(ApiError error, String traceId){
        return new ApiResponse<>(
                false,
                null,
                error,
                traceId,
                Instant.now()
        );
    }




}
