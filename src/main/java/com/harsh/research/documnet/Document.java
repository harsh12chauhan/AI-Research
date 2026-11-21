package com.harsh.research.documnet;

import com.harsh.research.common.SoftDeletableEntity;
import com.harsh.research.workspace.Workspace;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Document extends SoftDeletableEntity {

    @ManyToOne
    private Workspace workspace;

    @Column(nullable = false)
    private UUID uploadedBy;

    @Column(nullable = false)
    private String displayName;

    private String originalFileName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SourceType sourceType;

    @Column(nullable = false)
    private String mimeType;

    @Column(nullable = false)
    private long sizeBytes;

    @Column(nullable = false)
    private String checksumSha256;

    @Column(nullable = false)
    private String storageBucket;

    @Column(nullable = false)
    private String storageKey;

    private String extractedKey;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DocumentStatus status;

    private Integer pageCount;

    private Integer charCount;

    private String errorCode;

    private String errorMessage;

    public static Document create(
            Workspace workspace,
            UUID uploadedBy,
            String displayName,
            String originalFileName,
            SourceType sourceType,
            String mimeType,
            long sizeBytes,
            String checksumSha256,
            String storageBucket,
            String storageKey
    ){

        Document document = new Document();

        document.setWorkspace(workspace);
        document.setUploadedBy(uploadedBy);
        document.setDisplayName(displayName);
        document.setOriginalFileName(originalFileName);
        document.setSourceType(sourceType);
        document.setMimeType(mimeType);
        document.setSizeBytes(sizeBytes);
        document.setChecksumSha256(checksumSha256);
        document.setStorageBucket(storageBucket);
        document.setStorageKey(storageKey);

        return document;
    }


}
