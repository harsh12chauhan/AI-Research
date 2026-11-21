package com.harsh.research.documnet.storage;

import java.io.InputStream;
import java.time.Duration;

public interface StorageService {

    void put(String key, InputStream data, long contentLength, String contentType);

    InputStream get(String key);

    String getAsString(String key);

    PresignedUrl presignedGet(String key, Duration ttl);

    void delete(String key);

    boolean exists(String key);

}
