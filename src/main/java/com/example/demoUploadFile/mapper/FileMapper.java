package com.example.demoUploadFile.mapper;

import com.example.demoUploadFile.dto.FileResponse;
import com.example.demoUploadFile.entity.FileEntity;

public class FileMapper {
    public static FileResponse toDTO(FileEntity file) {
        FileResponse fileResponse = new FileResponse();
        fileResponse.setId(file.getId());
        fileResponse.setFileName(file.getFileName());
        fileResponse.setOriginalFileName(file.getOriginalName());
        fileResponse.setContentType(file.getFileType());
        fileResponse.setFilePath(file.getFilePath());
        fileResponse.setSize(file.getFileSize());
        return fileResponse;
    }
}
