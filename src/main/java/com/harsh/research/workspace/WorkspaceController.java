package com.harsh.research.workspace;

import com.harsh.research.common.ApiResponse;
import com.harsh.research.security.CurrentUser;
import com.harsh.research.workspace.dto.CreateWorkspaceRequest;
import com.harsh.research.workspace.dto.UpdateWorkspaceRequest;
import com.harsh.research.workspace.dto.WorkspaceResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/workspaces")
@RequiredArgsConstructor
public class WorkspaceController {

    private final WorkspaceService workspaceService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<WorkspaceResponse> create(@CurrentUser UUID userId,@Valid @RequestBody CreateWorkspaceRequest request) throws Exception {

        Workspace ws = workspaceService.create(userId, request);

        return ApiResponse.Ok(
                WorkspaceResponse.from(ws)
        );
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<List<WorkspaceResponse>> list(@CurrentUser UUID userId){

        List<Workspace> workspaces = workspaceService.listOfOwner(userId);

        List<WorkspaceResponse> response = workspaces.stream().map(
                WorkspaceResponse::from
        ).toList();

        return ApiResponse.Ok(
                response
        );
    }

    @GetMapping("/{wsId}")
    public ApiResponse<WorkspaceResponse> get(@CurrentUser UUID userId, @PathVariable UUID wsId) throws Exception {

        Workspace ws = workspaceService.get(wsId,userId);

        return ApiResponse.Ok(
                WorkspaceResponse.from(ws)
        );
    }

    @PatchMapping("/{wsId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<WorkspaceResponse> update(@CurrentUser UUID userId,@Valid @RequestBody UpdateWorkspaceRequest request,  @PathVariable UUID wsId) throws Exception {

        Workspace ws = workspaceService.update(wsId,userId,request);

        return ApiResponse.Ok(
                WorkspaceResponse.from(ws)
        );
    }

    @DeleteMapping("/{wsId}")
    public ApiResponse<Void> delete(@CurrentUser UUID userId, @PathVariable UUID wsId) throws Exception {

        workspaceService.delete(wsId,userId);
        return ApiResponse.Ok(null);
    }

}
