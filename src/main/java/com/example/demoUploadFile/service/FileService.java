package com.example.demoUploadFile.service;

import com.example.demoUploadFile.dto.FileResponse;
import com.example.demoUploadFile.entity.FileEntity;
import com.example.demoUploadFile.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {
    private final FileRepository fileRepository;

    // UPLOAD FILE
    public FileResponse upload(MultipartFile file) {
        // TRƯỚC KHI UPLOAD FILE CẦN KIỂM TRA FILE VÀ THƯ MỤC

        // kiểm tra file
        if (file.isEmpty()) {
            throw new RuntimeException("File is empty");
        }
        // Tạo Path đến thư mục
        Path uploadDir = Paths.get("uploads/images");

        // Tạo thư mục
        try {
            Files.createDirectories(uploadDir);

            // lấy tên file
            String originalFilename = file.getOriginalFilename();

            // lấy định dạng file (đuôi)
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));

            // tạo tên ngẫu nhiên
            String fileName = UUID.randomUUID().toString() + extension;

            // Tạo đường dẫn kết nối file với thư mục
            Path filePath = uploadDir.resolve(fileName);

            // Lưu trữ file trong server
            file.transferTo(filePath);

            // SAU KHI UPLOAD FILE TRONG SERVER THÌ LƯU THÔNG TIN FILE XUỐNG CSDL
            FileEntity fileEntity = new FileEntity();
            fileEntity.setFileName(fileName);
            fileEntity.setOriginalName(originalFilename);
            fileEntity.setFilePath(filePath.toString());
            fileEntity.setFileType(extension);
            fileEntity.setFileSize(file.getSize());
            fileRepository.save(fileEntity);
            return new FileResponse(fileEntity.getId(), fileName, originalFilename, file.getContentType(), filePath.toString(), file.getSize());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    // DOWNLOAD DỰA VÀO TÊN FILE
    public byte[] dowloadFile(String fileName) throws IOException {
        // Lấy file trong thư mục images
        Path path = Paths.get("uploads/images", fileName);
        if (!Files.exists(path)) {
            return null;
        }

        return Files.readAllBytes(path);
    }

    // DOWNLOAD DỰA VÀO ID
    public byte[] dowloadFileById(long id) throws IOException {
        // Tìm tên file dựa vào entity
        FileEntity fileInDB = fileRepository.findById(id).orElseThrow();

        // Lấy file trong thư mục
        Path path = Paths.get("uploads/images", fileInDB.getFileName());
        if (!Files.exists(path)) {
            return null;
        }

        return Files.readAllBytes(path);
    }

    // PHƯƠNG THỨC LẤY FILENAME DỰA VÀO ID
    public String getFileNameById(long id) {
        // Tìm tên file dựa vào entity
        FileEntity fileInDB = fileRepository.findById(id).orElseThrow();
        return fileInDB.getFileName();
    }

    // XOÁ FILE DỰA VÀO ID
    public String delete(long id) throws IOException {
        // Tìm fileName dựa vào entity
        FileEntity fileInDB = fileRepository.findById(id).orElseThrow();

        Path path = Paths.get("uploads/images", fileInDB.getFileName());

        if (!Files.exists(path)) {
            throw new RuntimeException("Không tìm thấy file");
        }

        Files.delete(path);
        fileRepository.deleteById(id);
        return "Xoá file thành công";
    }


}
