package com.example.demoUploadFile.controller;

import com.example.demoUploadFile.dto.FileResponse;
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

    // UPLOAD FILE
    @PostMapping("/upload")
    public ResponseEntity<FileResponse> upload(@RequestParam("file") MultipartFile multipartFile){
      return new ResponseEntity<>(fileService.upload(multipartFile), HttpStatus.OK);
    }

    // GET LẤY ẢNH DỰA VÀO TÊN FILE
    @GetMapping("/{fileName}")
    public ResponseEntity<byte[]> getFile(@PathVariable("fileName") String fileName){
        // Lấy file trong thư mục uploads
        Path path = Paths.get("uploads",fileName);
        if(!Files.exists(path)){
            return null;
        }
        try {
            byte[] data = Files.readAllBytes(path);
            String contentType = Files.probeContentType(path);
            if(contentType == null){
                contentType = "application/octet-stream";
            }
            return ResponseEntity.ok().header("Content-Type",contentType).body(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // DOWNLOAD VỀ MÁY
    @GetMapping("/download/{fileName}")
    public ResponseEntity<byte[]> download(@PathVariable("fileName") String fileName) throws IOException{
        // Lấy file trong thư mục uploads
        Path path = Paths.get("uploads",fileName);
        if(!Files.exists(path)){
            return null;
        }

        byte[] data = Files.readAllBytes(path);
        return ResponseEntity.ok().header(
                        "Content-Disposition",
                        "attachment; filename=\"" + fileName + "\""
                )
                .body(data);
    }
}
