package com.example.newsfeed.global.common.file.controller;

import com.example.newsfeed.global.common.file.service.FileService;
import com.example.newsfeed.global.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

import static com.example.newsfeed.global.common.constant.ImageUrlConst.IMAGE_URL;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping("/image")
    public Response<Map<String, String>> uploadImage(
        @RequestParam String type,
        @RequestParam MultipartFile file,
        @SessionAttribute Long sessionUserId
    ) {
        String imageUrl = fileService.uploadImage(type, file, sessionUserId);
        Map<String, String> response = new HashMap<>();
        response.put(IMAGE_URL, imageUrl);
        return Response.of(response, "이미지 저장 완료");
    }
}
