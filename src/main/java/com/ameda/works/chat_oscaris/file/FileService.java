package com.ameda.works.chat_oscaris.file;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileService {


    @Value("${application.file.uploads.media-output-path}")
    private String fileUploadPath;

    public String saveFile(@NonNull MultipartFile file,
                           @NonNull String userId) {
        final String fileUploadSubPath = "users" + File.separator + userId;
        return uploadFile(file,fileUploadSubPath);
    }

    private String uploadFile(
            @NonNull MultipartFile file,
            @NonNull String fileUploadSubPath) {
        final String finalUploadPath = fileUploadPath + File.separator + fileUploadSubPath;
        File targetFolder = new File(finalUploadPath);
        if ( !targetFolder.exists() ){
            boolean folderCreated = targetFolder.mkdirs();
            if(!folderCreated){
                log.warn("Failed to create a target folder: {}",targetFolder);
                return null;
            }
        }
        final String fileExtension = getFileExtension(file.getOriginalFilename());
        final String targetFilePath = finalUploadPath + File.separator +
                System.currentTimeMillis() + fileExtension;

        Path targetPath = Paths.get(targetFilePath);

        try{
            Files.write(targetPath,file.getBytes());
            log.info("File saved to: {}",targetPath);
            return targetFilePath;
        }catch (IOException ex){
            log.error("File was not saved: {}", ex.getMessage());
        }
        return null;
    }

    private String getFileExtension(String fileName) {
        if ( fileName == null || fileName.isEmpty() ){
            return "";
        }
        int lastDotIndex = fileName.lastIndexOf('.');

        if( lastDotIndex == -1 ){
            return "";
        }else {
            return fileName.substring(lastDotIndex + 1 ).toLowerCase();
        }
    }
}
