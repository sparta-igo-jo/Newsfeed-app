package com.example.newsfeed.global.common.file.controller;

import com.example.newsfeed.global.common.file.service.FileService;
import com.example.newsfeed.global.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.newsfeed.global.common.constant.ImageUrlConst.IMAGE_URL;
import static com.example.newsfeed.global.common.constant.SessionConst.LOGIN_USER;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping
    public Response<Map<String, String>> uploadImage(
        @RequestParam String type,
        @RequestParam MultipartFile file,
        @SessionAttribute(LOGIN_USER) Long sessionUserId
    ) {
        String imageUrl = fileService.uploadImage(type, file, sessionUserId);
        Map<String, String> response = new HashMap<>();
        response.put(IMAGE_URL, imageUrl);
        return Response.of(response, "이미지 저장 완료");
    }

    @PostMapping("/multiple")
    public Response<Map<String, List<String>>> uploadImage(
        @RequestParam("type") String type,
        @RequestParam("files") List<MultipartFile> files,
        @SessionAttribute(LOGIN_USER) Long sessionUserId
    ) {
        List<String> imageUrls = new ArrayList<>();
        for (MultipartFile file : files) {
            String imageUrl = fileService.uploadImage(type, file, sessionUserId);
            imageUrls.add(imageUrl);
        }
        Map<String, List<String>> response = new HashMap<>();
        response.put(IMAGE_URL, imageUrls);
        return Response.of(response, "다중 이미지 저장 완료");
    }
}
