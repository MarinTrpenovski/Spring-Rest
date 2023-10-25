package com.springgoals.service.impl;
import com.springgoals.service.FileService;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
public class FileServiceImpl implements FileService {

    @Value("${uploadDir}")
    private String uploadFolder;

    public static String uploadDirectory = System.getProperty("user.dir") + "/src/main/resources/images";

    private Path foundFile;

    @Override
    public void saveFile(String fileName, MultipartFile multipartFile, String entityFolder) throws IOException {

        Path uploadPath = Paths.get( uploadFolder );

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve( entityFolder + fileName );

            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException ioe) {
            throw new IOException("Could not save file: " + fileName, ioe);
        }

    }

    @Override
    public Resource loadFile(String fileName, String entityFolder) throws IOException {
        Path downloadPath = Paths.get(uploadFolder).resolve(entityFolder);

        Files.list(downloadPath).forEach(file -> {
            if (file.getFileName().toString().startsWith(fileName)) {
                foundFile = file;
                return;
            }
        });

        if (foundFile != null) {
            return new UrlResource(foundFile.toUri());
        }

        return null;
    }

}
