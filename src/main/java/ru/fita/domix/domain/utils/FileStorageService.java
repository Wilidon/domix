package ru.fita.domix.domain.utils;

import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.fita.domix.domain.utils.exceptions.FileNotFoundException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    /**
     * Конструктор сервиса сохранения файлов.
     * При создании необходимо указывать в application.properties директорию, куда процесс может сохранять файлы. Если доступ к директории не получен, бросает RuntimeException.
     * @param uploadDirectory Абсолютный путь до директории, где можно сохранять файлы.
     */
    @Autowired
    public FileStorageService(@Value("${file-storage.upload-directory}") String uploadDirectory) {
        this.fileStorageLocation = Paths.get(uploadDirectory)
                .toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception e) {
            throw new RuntimeException("Невозможно создать папку для сохранения файлов.", e);
        }
    }

    /**
     * Сохранение файла в файловую систему.
     * Сохраняет полученный из составного запроса файл в директорию для сохранения. Если файл не получилось по какой-то причине сохранить, бросает RuntimeException.
     * @param file Файл из составного запроса
     * @return Абсолютный путь в системе до сохраненного файла.
     */
    public String saveFile(@NotNull MultipartFile file) {

        // Валидация имени файла
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        try {
            if(fileName.contains("..")) {
                throw new RuntimeException("Имя файла содержит недопустимую последовательность " + fileName);
            }

            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException e) {
            throw new RuntimeException("Невозможно сохранить файл " + fileName, e);
        }
    }

    /**
     * Загрузка файла из файловой системы.
     * Ищет файл по его имени в директории для сохранения, затем возращает в виде ресурса. В случае, если файл по каким-то причинам был не найден, бросает FileNotFoundException.
     * @param fileName Имя файла в файловой системе
     * @return Файл в формате UrlResourse.
     */
    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new FileNotFoundException("Файл не найден " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new FileNotFoundException("Файл не найден " + fileName + " по причине:", ex);
        }
    }
}
