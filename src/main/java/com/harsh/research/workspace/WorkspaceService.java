package com.harsh.research.workspace;

import com.harsh.research.workspace.dto.CreateWorkspaceRequest;
import com.harsh.research.workspace.dto.UpdateWorkspaceRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WorkspaceService {

    private final WorkspaceRepository workspaceRepository;

    public Workspace create(UUID ownerId, CreateWorkspaceRequest request) throws Exception {

        if(workspaceRepository.existsByOwnerAndName(ownerId, request.name())){
            throw new Exception("Workspace already exits with name");
        }

        Workspace workspace = Workspace.create(ownerId,request.name(),request.description());

        return workspaceRepository.save(workspace);

    }

    public List<Workspace> listOfOwner(UUID ownerId){

        return workspaceRepository.findAllByOwnerIdAndDeletedAtNullOrderByCreatedAtDesc(ownerId);

    }

    public Workspace get(UUID workspaceId, UUID ownerId) throws Exception {

        return workspaceRepository.findByIdAndOwnerIdAndDeletedAtNull(workspaceId,ownerId)
                .orElseThrow(()-> new Exception("Workspace not exits")
                );

    }

    public Workspace update(UUID workspaceId, UUID ownerId, UpdateWorkspaceRequest request) throws Exception {

        Workspace ws = get(workspaceId,ownerId);

        if(ws.getName() != null && !request.name().equals(ws.getName())){
            if(workspaceRepository.existsByOwnerAndName(ownerId, request.name())){
                throw new Exception("Workspace already exits with name");
            }

            ws.setName(request.name());
        }


        if(request.description() != null){
            ws.setDescription(request.description());
        }

        return workspaceRepository.save(ws);
    }

    public void delete(UUID workspaceId, UUID ownerId) throws Exception {

        Workspace ws = get(workspaceId,ownerId);

        ws.softDelete();

        workspaceRepository.save(ws);

    }

}
