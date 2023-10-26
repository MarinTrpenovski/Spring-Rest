package com.springgoals.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface FileService {

    void saveFile(String fileName, MultipartFile multipartFile, String entityFolder) throws IOException;


    Resource loadFile(String fileName, String entityFolder) throws IOException;


    void deleteAll(String entityFolder);

    Stream<Path> loadAll(String entityFolder);
}
