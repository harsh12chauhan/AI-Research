package com.harsh.research.security;

import com.harsh.research.common.ApiError;
import com.harsh.research.common.ApiResponse;
import com.harsh.research.common.ErrorCode;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RestAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {

        response.setStatus(ErrorCode.FORBIDDEN.status().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        String traceId = UUID.randomUUID().toString();
        ApiError error = ApiError.of(String.valueOf(ErrorCode.FORBIDDEN),"Access Denied");


        response.getWriter()
                .write(
                        objectMapper.writeValueAsString(
                                ApiResponse.error(
                                        error,traceId
                                )
                        )
                );


    }
}
