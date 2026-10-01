package com.example.demoUploadFile.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileCloudResponse {
    private String fileName;
    private String originalFileName;
    private String contentType;
    private long size;
    private String filePath;
}
