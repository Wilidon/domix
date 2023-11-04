package ru.fita.domix.web;

import lombok.AllArgsConstructor;
import org.apache.commons.compress.utils.FileNameUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.fita.domix.domain.storage.StorageService;
import ru.fita.domix.domain.storage.dto.StorageOutput;

import java.io.IOException;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class StorageApiController implements StorageApi {
    private final StorageService storageService;


    public ResponseEntity<StorageOutput> uploadImage(@RequestPart(name = "file") MultipartFile image) throws IOException {
        String extension = FileNameUtils.getExtension(image.getOriginalFilename());
        StorageOutput storageOutput = storageService.saveFile(extension, image.getInputStream());

        return ResponseEntity.ok(storageOutput);

    }
}
