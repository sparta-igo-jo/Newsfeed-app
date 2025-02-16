package com.example.newsfeed.global.common.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class FileStorageService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    //TODO: 이미지를 로컬에 저장하는 기능만 있음. 삭제 기능? 도 필요해 보임.
    public String storeFile(MultipartFile image, String email) {
        try {
            if (image.isEmpty()) {
                return null;
            }
            LocalDate date = LocalDate.now();
            String dirPath = String.format("%s/%s/%d/%02d/%02d",
                    uploadDir, email, date.getYear(), date.getMonthValue(), date.getDayOfMonth());

            Path directory = Paths.get(dirPath);
            // directory : 파일을 저장할 경로
            // !Files.exists(directory) : 해당 경로가 존재하는지 확인 (존재하지 않으면 생성)
            if (!Files.exists(directory)) {
                Files.createDirectories(directory);
            }

            String imageName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
            Path imagePath = directory.resolve(imageName);
            image.transferTo(imagePath.toFile());

            return imagePath.toString();
        } catch (IOException e) {
            throw new RuntimeException("failed to save image", e);
        }
    }
}
