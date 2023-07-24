package com.Petching.petching.utils;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class GenerateName {
    public String generateFileName(String originalFilename) {
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String uniqueFileName = UUID.randomUUID().toString().replace("-", "");
        return uniqueFileName + extension;
    }

    public String generateRandomName(){
        return UUID.randomUUID().toString();
    }
}
