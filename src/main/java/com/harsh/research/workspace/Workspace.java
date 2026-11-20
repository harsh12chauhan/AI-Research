package com.harsh.research.workspace;

import com.harsh.research.common.SoftDeletableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Workspace extends SoftDeletableEntity {

    @Column(nullable = false)
    private UUID ownerId;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private int docCount = 0;

    @Column(nullable = false)
    private int chatCount = 0;

    @Column(nullable = false)
    private long byteCount = 0;

    public static Workspace create( UUID ownerId, String name, String description){

        Workspace ws = new Workspace();

        ws.setOwnerId(ownerId);
        ws.setName(name);
        ws.setDescription(description);

        return ws;
    }


}
