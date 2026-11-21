package com.harsh.research.documnet.storage;

import com.harsh.research.config.StorageProperties;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class B2StorageService implements StorageService{

    private final S3Presigner s3Presigner;
    private final S3Client s3Client;
    private final StorageProperties storageProperties;

    @Override
    public void put(String key, InputStream data, long contentLength, String contentType) {

        byte[] bytes;

        try {
            bytes = data.readAllBytes();
        } catch (IOException ioe) {
            throw new UncheckedIOException("Failed to read data from " + key , ioe);
        }

        s3Client.putObject(
                PutObjectRequest.builder()
                        .bucket(storageProperties.bucket())
                        .key(key)
                        .contentLength(contentLength)
                        .contentType(contentType)
                        .build(),
                RequestBody.fromBytes(bytes)
        );
    }

    @Override
    public InputStream get(String key) {

        return s3Client.getObject(
                GetObjectRequest.builder()
                        .bucket(storageProperties.bucket())
                        .key(key)
                        .build()
        );
    }

    @Override
    public String getAsString(String key) {

        try (InputStream in = get(key)){

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            in.transferTo(out);
            return out.toString(StandardCharsets.UTF_8);

        }catch (IOException ioe) {
            throw new UncheckedIOException("Failed to read data from " + key , ioe);
        }
    }

    @Override
    public PresignedUrl presignedGet(String key, Duration ttl) {

        var presignedUrl = s3Presigner.presignGetObject(
                GetObjectPresignRequest.builder()
                        .signatureDuration(ttl)
                        .getObjectRequest(
                                GetObjectRequest.builder()
                                        .bucket(storageProperties.bucket())
                                        .key(key)
                                        .build()
                        )
                        .build()
        );

        return new PresignedUrl(presignedUrl.url().toString(), Instant.now().plus(ttl));
    }

    @Override
    public void delete(String key) {

        s3Client.deleteObject(
                DeleteObjectRequest.builder()
                        .bucket(storageProperties.bucket())
                        .key(key)
                        .build()
        );
    }

    @Override
    public boolean exists(String key) {

        try{
            s3Client.headObject(
                    HeadObjectRequest.builder()
                            .bucket(storageProperties.bucket())
                            .key(key)
                            .build()
            );

            return true;

        }catch(NoSuchKeyException e){
            return false;
        }
    }
}
