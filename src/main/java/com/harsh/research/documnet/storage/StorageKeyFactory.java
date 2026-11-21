package com.harsh.research.documnet.storage;

import com.harsh.research.config.StorageProperties;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.regex.Pattern;

@Component
public class StorageKeyFactory {

    private static final Pattern UNSAFE_CHARS= Pattern.compile("[^A-Za-z0-9._-]]");

    private static final int MAX_FILENAME_LENGTH = 180;

    private final String keyPrefix;

    public StorageKeyFactory(StorageProperties storageProperties){

        String prefix = storageProperties.keyPrefix();

        if(prefix == null || prefix.isBlank()){
            this.keyPrefix = "";
        }else{
            this.keyPrefix = prefix.endsWith("/")?prefix:prefix+"/";
        }
    }

    public String documentPrefix(UUID workspaceId, UUID documentId){
        return keyPrefix
                + "w/" + workspaceId
                + "d/" + documentId
                + "/";

    }

    public String originalKey(UUID workspaceId, UUID documentId,String fileName){
        return documentPrefix(workspaceId,documentId)
                + "original/"
                + sanitize(fileName);
    }

    private String sanitize(String fileName){

        if(fileName == null || fileName.isBlank()){
            return "file";
        }

        String cleaned = UNSAFE_CHARS.matcher(fileName).replaceAll("_");

        return cleaned.length()>MAX_FILENAME_LENGTH
                ? cleaned.substring(0,MAX_FILENAME_LENGTH)
                : cleaned;
    }
}
