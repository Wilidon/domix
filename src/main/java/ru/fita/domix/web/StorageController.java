package ru.fita.domix.web;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.fita.domix.domain.storage.StorageService;
import ru.fita.domix.domain.storage.dto.StorageOutput;

import java.io.IOException;

@RestController
@RequestMapping("/storages")
@AllArgsConstructor
@Tag(name = "S3-хранилище")
@CrossOrigin(origins = "*")
public class StorageController {
    private final StorageService storageService;

    @RequestMapping(value = "",
            method = RequestMethod.POST,
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    private ResponseEntity<StorageOutput> uploadImage(@RequestPart(name = "file") MultipartFile image) throws IOException {
        StorageOutput storageOutput = storageService.saveFile(image.getInputStream());

        return ResponseEntity.ok(storageOutput);

    }
}
