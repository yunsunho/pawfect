package com.example.Pawfect.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileStorageService {

    // 실제 저장될 경로 (예: src/main/resources/static/upload/)
    private final String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/upload/";

    public String save(MultipartFile file) throws IOException {
        // 디렉토리가 없으면 생성
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // 파일 이름 생성 (UUID로 중복 방지)
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String uniqueFilename = UUID.randomUUID() + extension;

        // 파일 저장
        File savedFile = new File(uploadDir + uniqueFilename);
        file.transferTo(savedFile);

        // 웹에서 접근 가능한 경로 리턴 (static 기준)
        return "/upload/" + uniqueFilename;
    }
}
