package ru.fita.domix.domain.storage.s3;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Класс предоставляет доступ к S3-хранилищу Minio. Для корректной работы необходимо
 * вставить данные для подключения к Minio в application.properties.
 * Класс реализует интерфейс {@link ru.fita.domix.domain.storage.s3.S3}.
 */
@Service(value = "minios3")
public class MinioS3 implements S3 {
    private final MinioClient minioClient;
    private final String bucket;

    public MinioS3(@Value("${s3-minio.endpoint}") String endpoint,
                   @Value("${s3-minio.accessKey}") String accessKey,
                   @Value("${s3-minio.secretKey}") String secretKey,
                   @Value("${s3-minio.bucket}") String bucket) {
        this.bucket = bucket;
        minioClient =
                MinioClient.builder().
                        endpoint(endpoint)
                        .credentials(accessKey,
                                secretKey)
                        .build();
    }


    @Override
    public void saveFile(String key, InputStream file) {
        try {
            minioClient.putObject(
                    PutObjectArgs.builder().bucket(this.bucket).object(key)
                            .stream(
                                    file, file.available(), -1)
                            .build());
        } catch (ErrorResponseException e) {
            throw new RuntimeException(e);
        } catch (InsufficientDataException e) {
            throw new RuntimeException(e);
        } catch (InternalException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (InvalidResponseException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (ServerException e) {
            throw new RuntimeException(e);
        } catch (XmlParserException e) {
            throw new RuntimeException(e);
        }
    }
}
