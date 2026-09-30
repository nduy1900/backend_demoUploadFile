package com.example.demoUploadFile.service;

import com.example.demoUploadFile.dto.FileResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileService {

    // UPLOAD FILE
    public FileResponse upload(MultipartFile file){
        // kiểm tra file
        if(file.isEmpty()){
            throw new RuntimeException("File is empty");
        }

        // Tạo Path đến thư mục
        Path uploadDir = Paths.get("uploads");

        // Tạo thư mục
        try {
            Files.createDirectories(uploadDir);

            // lấy tên file
            String originalFilename = file.getOriginalFilename();

            // lấy định dạng file (đuôi)
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));

            // tạo tên ngẫu nhiên
            String fileName = UUID.randomUUID().toString() + extension;
            System.out.println(fileName);

            Path filePath = uploadDir.resolve(fileName);

            // sao chép vào thư mục uploads
            file.transferTo(filePath);

            return new FileResponse(fileName,originalFilename,file.getContentType(),file.getSize());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }




}
