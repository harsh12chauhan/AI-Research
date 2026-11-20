package com.harsh.research.workspace.dto;

import com.harsh.research.workspace.Workspace;

import java.time.Instant;
import java.util.UUID;

public record WorkspaceResponse(
        UUID id,
        String name,
        String description,
        int docCount,
        int chatCount,
        long byteCount,
        Instant createdAt,
        Instant UpdatedAt
) {

    public static WorkspaceResponse from(Workspace ws){

        return new WorkspaceResponse(
                ws.getId(),
                ws.getName(),
                ws.getDescription(),
                ws.getDocCount(),
                ws.getChatCount(),
                ws.getByteCount(),
                ws.getCreatedAt(),
                ws.getUpdatedAt()
        );
    }
}
