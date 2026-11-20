package com.harsh.research.workspace.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateWorkspaceRequest(

        @Size(max=120)
        String name,

        @Size(max=500)
        String description

) {
}
