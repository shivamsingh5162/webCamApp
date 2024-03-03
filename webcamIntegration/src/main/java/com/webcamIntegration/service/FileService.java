package com.webcamIntegration.service;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class FileService {

    private final String storageDirectory = "src/main/resources/video"; // Adjust the path as needed

    public List<String> getStoredFiles() {
        File storageDir = new File(storageDirectory);
        String[] fileNames = storageDir.list();
        return Arrays.asList(fileNames != null ? fileNames : new String[0]);
    }
}