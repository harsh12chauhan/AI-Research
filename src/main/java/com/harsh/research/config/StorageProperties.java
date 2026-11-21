package com.harsh.research.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.storage")
public record StorageProperties(
        String endpoint,
        String region,
        String bucket,
        String keyPrefix,
        String keyId,
        String applicationKey,
        boolean pathStyleAccess
) {
}
