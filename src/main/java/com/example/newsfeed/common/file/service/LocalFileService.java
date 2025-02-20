package com.example.newsfeed.common.file.service;

import com.example.newsfeed.common.exception.BaseException;
import com.example.newsfeed.common.exception.ErrorDetail;
import com.example.newsfeed.common.file.FileType;
import com.example.newsfeed.domain.user.application.service.UserReadService;
import com.example.newsfeed.domain.user.entity.User;
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
import java.util.*;

import static com.example.newsfeed.common.constant.ImageUrlConst.DEFAULT_PROFILE_IMAGE_PATH;
import static com.example.newsfeed.common.exception.ErrorCode.*;
import static com.example.newsfeed.common.file.FileType.from;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@Service
@RequiredArgsConstructor
public class LocalFileService implements FileService {

    @Value("${base.dir}")
    private String baseDir;

    private static final List<String> ALLOWED_IMAGE_TYPES = new ArrayList<>(List.of("image/jpeg", "image/png"));

    private final UserReadService userReadService;

    @Override
    public String uploadImage(String type, MultipartFile file, Long sessionUserId) {
        // 이미지 타입 검증
        if (!isValidImage(file)) {
            throw new BaseException(List.of(
                new ErrorDetail(INVALID_MIME_TYPE, "file", INVALID_MIME_TYPE.getMessage())
            ));
        }

        // 파일 확장자 검증
        if (!hasValidImageExtensions(file)) {
            throw new BaseException(List.of(
                new ErrorDetail(INVALID_FILE_EXTENSION, "file", INVALID_FILE_EXTENSION.getMessage())
            ));
        }

        User getSessionUser = userReadService.findUserById(sessionUserId);

        // 업로드하는 타입에 따라 하위 폴더명 설정
        FileType fileType = from(type);

        // 기본 이미지 변경 요청일 경우, 파일 업로드 없이 바로 기본 프로필 이미지 경로 반환
        String subDirectory = fileType.resolveSubDirectory(file, DEFAULT_PROFILE_IMAGE_PATH);

        // 이메일 암호화
        String convertEmail = DigestUtils.md5DigestAsHex(getSessionUser.getEmail().getBytes());

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
            throw new BaseException(List.of(
                new ErrorDetail(UPLOAD_FAILED, "file", UPLOAD_FAILED.getMessage())
            ));
        }
    }

    private boolean isValidImage(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && ALLOWED_IMAGE_TYPES.contains(contentType);
    }

    private boolean hasValidImageExtensions(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if (fileName == null) {
            return false;
        }
        String extension = fileName.substring(fileName.lastIndexOf("." + 1)).toLowerCase();
        return Arrays.asList("jpg", "jpeg", "png").contains(extension);
    }
}
