package com.ameda.works.chat_oscaris.file;

import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
public class Utility {

    private Utility() {
    }

    public static byte [] readfile( String fileUrl  ){

        if( StringUtils.isBlank(fileUrl) ){
            return new byte[0];
        }

        try{
            Path file = new File(fileUrl).toPath();
            return Files.readAllBytes(file);
        }catch ( IOException ex ){
            log.warn("No file found in path: {}",fileUrl);
        }
        return new byte[0];
    }
}
