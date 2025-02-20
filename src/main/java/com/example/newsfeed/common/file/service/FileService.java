package com.example.newsfeed.common.file.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    // type 정보에 따라 profile 또는 feed로 분리
    String uploadImage(String type, MultipartFile file, Long sessionUserId);
}
