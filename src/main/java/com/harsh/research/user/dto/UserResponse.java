package com.harsh.research.user.dto;

import com.harsh.research.user.User;

import java.time.Instant;
import java.util.UUID;

public record UserResponse(
        UUID Id,
        String email,
        String fullName,
        String role,
        boolean emailVerified,
        Instant createdAt
) {

    public static UserResponse from(User user){

        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getFullName(),
                user.getRole().name(),
                user.isEmailVerified(),
                user.getCreatedAt()

        );
    }
}
