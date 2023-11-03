package ru.fita.domix.domain.storage.s3;

import java.io.InputStream;

public interface S3 {
    void saveFile(String key, InputStream inputStream);
}
