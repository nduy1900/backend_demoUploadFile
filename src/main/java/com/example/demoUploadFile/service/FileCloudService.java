package com.example.demoUploadFile.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.demoUploadFile.dto.FileCloudResponse;
import com.example.demoUploadFile.dto.FileResponse;
import com.example.demoUploadFile.entity.FileEntity;
import com.example.demoUploadFile.mapper.FileMapper;
import com.example.demoUploadFile.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FileCloudService {
    private final Cloudinary cloudinary;
    private final FileRepository fileRepository;

    public FileCloudResponse upload(MultipartFile file) throws IOException {
        Map result = cloudinary.uploader().upload(
                file.getBytes(),
                ObjectUtils.asMap(
                        "folder", "uploads"
                )
        );

        FileCloudResponse cloudResponse = new FileCloudResponse();
        cloudResponse.setFileName(result.get("public_id").toString());
        cloudResponse.setOriginalFileName(file.getOriginalFilename());
        cloudResponse.setContentType(file.getContentType());
        cloudResponse.setSize(file.getSize());
        cloudResponse.setFilePath(result.get("secure_url").toString());

        // Lưu thông tin vào trong DB
        FileEntity fileEntity = new FileEntity();
        fileEntity.setFileName(result.get("public_id").toString());
        fileEntity.setOriginalName(file.getOriginalFilename());
        fileEntity.setFilePath(result.get("secure_url").toString());
        fileEntity.setFileType(file.getContentType());
        fileEntity.setFileSize(file.getSize());
        fileRepository.save(fileEntity);
        return cloudResponse;
    }

    // GET ALL FILE
    public List<FileResponse> getAll() {
        return fileRepository.findAll()
                .stream()
                .map(FileMapper::toDTO).toList();
    }

    // GET ĐƯỜNG DẪN FILE THEO ID
    public FileResponse getFileById(long id) {
        return FileMapper.toDTO(fileRepository.findById(id).orElseThrow());
    }

    // XOÁ FILE
    public String deleteFile(long id) throws IOException {
        FileEntity fileInDB = fileRepository.findById(id).orElse(null);
        // XOÁ Ở CLOUDINARY
        cloudinary.uploader().destroy(fileInDB.getFileName(), ObjectUtils.emptyMap());

        // XOÁ Ở CSDL
        fileRepository.deleteById(id);
        return "Xoá thành công";
    }
}
