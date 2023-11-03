package ru.fita.domix.web;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.apache.commons.compress.utils.FileNameUtils;
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
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Метод для загрузки изображения в S3-хранилище. " +
                            "Возвращает ссылку на файл. Максимальный размер файла - 10мб.",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = StorageOutput.class))
                    }),
            @ApiResponse(
                    responseCode = "500",
                    description = "Ошибка на стороне сервера.",
                    content = {
                            @Content()
                    }
            )
    })
    private ResponseEntity<StorageOutput> uploadImage(@RequestPart(name = "file") MultipartFile image) throws IOException {
        String extension = FileNameUtils.getExtension(image.getOriginalFilename());
        StorageOutput storageOutput = storageService.saveFile(extension, image.getInputStream());

        return ResponseEntity.ok(storageOutput);

    }
}
