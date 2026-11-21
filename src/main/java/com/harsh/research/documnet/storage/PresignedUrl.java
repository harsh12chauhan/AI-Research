package com.harsh.research.documnet.storage;

import java.time.Instant;

public record PresignedUrl(
        String url,
        Instant expiredAt
) {
}
