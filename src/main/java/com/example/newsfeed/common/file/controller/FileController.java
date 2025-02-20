package com.example.newsfeed.common.file.controller;

import com.example.newsfeed.common.file.service.FileService;
import com.example.newsfeed.common.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.newsfeed.common.constant.ImageUrlConst.IMAGE_URL;
import static com.example.newsfeed.common.constant.SessionConst.LOGIN_USER;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping(consumes = MULTIPART_FORM_DATA_VALUE)
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
}
