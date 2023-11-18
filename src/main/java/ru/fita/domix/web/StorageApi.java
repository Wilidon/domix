package ru.fita.domix.web;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import ru.fita.domix.domain.storage.dto.StorageOutput;

import java.io.IOException;

@Validated
@RequestMapping("/storages")
@Tag(name = "S3-хранилище")
public interface StorageApi {
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
    @RequestMapping(value = "",
            method = RequestMethod.POST,
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<StorageOutput> uploadImage(@RequestPart(name = "file") MultipartFile image) throws IOException;
}
