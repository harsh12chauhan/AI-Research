package com.harsh.research.workspace;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface WorkspaceRepository extends JpaRepository<Workspace, UUID> {

    Optional<Workspace> findByIdAndOwnerIdAndDeletedAtNull(UUID id, UUID ownerId);

    List<Workspace> findAllByOwnerIdAndDeletedAtNullOrderByCreatedAtDesc(UUID ownerId);

    long countByOwnerIdAndDeletedAtNullOrderByCreatedAtDesc(UUID ownerId);

    @Query(" Select (count(w)>0) from Workspace w "
            + " where w.ownerId=:ownerId and "
            + " lower( w.name ) = lower(:name) and "
            + " w.deletedAt is null ")
    boolean existsByOwnerAndName(@Param("ownerId") UUID ownerId,
                                 @Param("name") String name);

}
