package com.example.newsfeed.global.common.file.service;

import com.example.newsfeed.global.common.exception.ErrorDetail;
import com.example.newsfeed.global.common.file.exception.FileUploadFailedException;
import com.example.newsfeed.user.application.service.UserService;
import com.example.newsfeed.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.example.newsfeed.global.common.exception.ErrorCode.UPLOAD_FAILED;
import static com.example.newsfeed.global.common.file.FileType.FEEDS;
import static com.example.newsfeed.global.common.file.FileType.PROFILE;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@Service
@RequiredArgsConstructor
public class LocalFileService implements FileService {

    @Value("${base.dir}")
    private String baseDir;

    private final UserService userService;

    @Override
    public String uploadImage(String type, MultipartFile file, Long sessionUserId) {
        User getSessionUser = userService.findUserById(sessionUserId);

        // 이메일 암호화
        String convertEmail = DigestUtils.md5DigestAsHex(getSessionUser.getEmail().getBytes());

        // 업로드하는 타입에 따라 하위 폴더명 설정
        String subDirectory;
        if (PROFILE.getType().equalsIgnoreCase(type)) {
            subDirectory = "profile";
        } else if (FEEDS.getType().equalsIgnoreCase(type)) {
            subDirectory = "feeds";
        } else {
            subDirectory = "others";
        }

        // 업로드하는 시간을 가져와서 연/월/일로 변환
        String datePath = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

        // 파일 업로드 디렉토리
        String uploadDir = baseDir + "/" + convertEmail + "/" + subDirectory + "/" + datePath;

        // `UUID_원본파일`로 최종 파일 이름을 만들기 위함
        // file의 이름을 경로에 안전하게 변환
        String fileName = UUID.randomUUID() + "_" + StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        try {
            // 파일 저장 경로 가져오기
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                // 경로에 폴더가 없을 경우 생성
                Files.createDirectories(uploadPath);
            }

            // uploadPath에 fileName을 더한 최종 경로를 반환
            Path filePath = uploadPath.resolve(fileName);
            
            // file.getInputStream()으로 읽은 뒤 filePath에 동일한 파일이 있을 경우 덮어쓰기 후 저장
            Files.copy(file.getInputStream(), filePath, REPLACE_EXISTING);

            return "/files/" + convertEmail + "/" + subDirectory + "/" + datePath + "/" + fileName;
        } catch (IOException ioe) {
            throw new FileUploadFailedException(List.of(
                new ErrorDetail(UPLOAD_FAILED, "file", UPLOAD_FAILED.getMessage())
            ));
        }
    }
}
