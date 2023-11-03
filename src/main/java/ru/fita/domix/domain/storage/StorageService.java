package ru.fita.domix.domain.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.fita.domix.domain.storage.dto.StorageOutput;
import ru.fita.domix.domain.storage.s3.S3;

import java.io.InputStream;
import java.util.UUID;

@Service
public class StorageService {

    private final S3 storage;
    private final String s3Domain;
    private final String s3Bucket;

    @Autowired
    public StorageService(@Qualifier("minios3") S3 storage,
                          @Value("${s3-minio.domain}") String s3Domain,
                          @Value("${s3-minio.bucket}") String s3Bucket) {
        this.storage = storage;
        this.s3Domain = s3Domain;
        this.s3Bucket = s3Bucket;
    }

    public StorageOutput saveFile(String extension, InputStream inputStream) {
        String filename = UUID.randomUUID() + "." + extension;
        storage.saveFile(filename, inputStream);
        String urlToFile = this.s3Domain + "/" + this.s3Bucket + "/" + filename;

        return new StorageOutput(urlToFile);
    }

}
