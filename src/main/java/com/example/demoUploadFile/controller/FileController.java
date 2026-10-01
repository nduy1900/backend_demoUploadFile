package com.example.demoUploadFile.controller;

import com.example.demoUploadFile.dto.FileResponse;
import com.example.demoUploadFile.entity.FileEntity;
import com.example.demoUploadFile.repository.FileRepository;
import com.example.demoUploadFile.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/files")
public class FileController {
    @Autowired
    private FileService fileService;

    @Autowired
    private FileRepository fileRepository;

    // UPLOAD FILE
    @PostMapping("/uploads")
    // @RequestParam("file") : tên param phải tương ứng với tên field mà client gửi
    // "file" là tên field trong HTTP Request, còn multipartFile là tên biến Java
    public ResponseEntity<FileResponse> upload(@RequestParam("file") MultipartFile multipartFile) {
        return new ResponseEntity<>(fileService.upload(multipartFile), HttpStatus.OK);
    }

    // GET LẤY ẢNH DỰA VÀO TÊN FILE
    @GetMapping("/name/{fileName}")
    public ResponseEntity<byte[]> getFile(@PathVariable("fileName") String fileName) throws IOException {
        // Lấy file trong thư mục images
        Path path = Paths.get("uploads/images", fileName);
        if (!Files.exists(path)) {
            return null;
        }

        byte[] data = Files.readAllBytes(path);
        String contentType = Files.probeContentType(path);
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok().header("Content-Type", contentType).body(data);

    }

    // GET HÌNH ẢNH DỰA VÀO ID
    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getFileById(@PathVariable long id) throws IOException {
        // Tìm tên file dựa vào entity
        FileEntity fileInDB = fileRepository.findById(id).orElseThrow();

        // Lấy file trong thư mục
        Path path = Paths.get("uploads/images", fileInDB.getFileName());
        if (!Files.exists(path)) {
            return null;
        }
        byte[] data = Files.readAllBytes(path);
        String contentType = Files.probeContentType(path);
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok().header("Content-Type", contentType).body(data);
    }

    // DOWNLOAD FILE VỀ MÁY DỰA VÀO TÊN FILE
    @GetMapping("/download/name/{fileName}")
    public ResponseEntity<byte[]> download(@PathVariable("fileName") String fileName) throws IOException {

        return ResponseEntity.ok().header(
                        "Content-Disposition",
                        "attachment; filename=\"" + fileName + "\"")
                .body(fileService.dowloadFile(fileName));
    }

    // DOWNLOAD FILE VỀ MÁY DỰA VÀO ID
    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadFileById(@PathVariable long id) throws IOException {

        return ResponseEntity.ok().header(
                        "Content-Disposition",
                        "attachment; filename=\"" + fileService.getFileNameById(id) + "\"")
                .body(fileService.dowloadFileById(id));
    }

    // XOÁ FILE
//    @DeleteMapping("/{fileName}")
//    public ResponseEntity<String> deleteFile(@PathVariable("fileName") String fileName) throws IOException {
//        return ResponseEntity.ok(fileService.delete(fileName));
//    }

    // XOÁ FILE DỰA VÀO ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFile(@PathVariable long id) throws IOException {
        return ResponseEntity.ok().body(fileService.delete(id));
    }
}
