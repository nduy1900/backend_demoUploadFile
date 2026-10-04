package com.example.demoUploadFile.controller;

import com.example.demoUploadFile.dto.FileCloudResponse;
import com.example.demoUploadFile.dto.FileResponse;
import com.example.demoUploadFile.service.FileCloudService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/clouds/file")
@RequiredArgsConstructor
public class FileCloudController {
    private final FileCloudService fileCloudService;

    // UPLOAD FILE
    @PostMapping("/upload")
    public ResponseEntity<FileCloudResponse> upload(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        return ResponseEntity.ok().body(fileCloudService.upload(multipartFile));
    }

    // GET ALL FILE (CSDL) viết ở server hay cloud đều được
    @GetMapping()
    public ResponseEntity<List<FileResponse>> getAll() {
        return ResponseEntity.ok().body(fileCloudService.getAll());
    }

    // GET ĐƯỜNG LINK FILE THEO ID
    @GetMapping("/{id}")
    public ResponseEntity<FileResponse> getFileById(@PathVariable String id) {
        return ResponseEntity.ok().body(fileCloudService.getFileById(Long.parseLong(id)));
    }

    // XOÁ FILE
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFileById(@PathVariable String id) throws IOException {
        return ResponseEntity.ok().body(fileCloudService.deleteFile(Long.parseLong(id)));
    }
}
