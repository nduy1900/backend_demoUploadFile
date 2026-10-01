package com.example.demoUploadFile.controller;

import com.example.demoUploadFile.dto.FileCloudResponse;
import com.example.demoUploadFile.service.FileCloudService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/clouds/file")
@RequiredArgsConstructor
public class FileCloudController {
    private final FileCloudService fileCloudService;

    @PostMapping("/upload")
    public ResponseEntity<FileCloudResponse> upload(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        return ResponseEntity.ok().body(fileCloudService.upload(multipartFile));
    }
}
