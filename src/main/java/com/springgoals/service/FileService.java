package com.springgoals.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {

    void saveFile(String fileName, MultipartFile multipartFile, String entityFolder) throws IOException;


    Resource loadFile(String fileName, String entityFolder) throws IOException;
}
