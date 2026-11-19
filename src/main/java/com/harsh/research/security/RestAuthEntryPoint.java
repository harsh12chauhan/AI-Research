package com.harsh.research.security;

import com.harsh.research.common.ApiError;
import com.harsh.research.common.ApiResponse;
import com.harsh.research.common.ErrorCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.UUID;


@Component
@RequiredArgsConstructor
public class RestAuthEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {

        response.setStatus(ErrorCode.UNAUTHORIZED.status().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        String traceId = UUID.randomUUID().toString();
        ApiError error = ApiError.of(String.valueOf(ErrorCode.UNAUTHORIZED),"Authentication is required");


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
