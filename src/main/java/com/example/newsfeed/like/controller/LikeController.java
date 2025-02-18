package com.example.newsfeed.like.controller;

import com.example.newsfeed.global.response.Response;
import com.example.newsfeed.like.application.service.LikeWriteService;
import com.example.newsfeed.like.dto.LikeResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.example.newsfeed.global.common.constant.SessionConst.LOGIN_USER;

@RestController
@RequestMapping("/feeds/{feedId}/like")
@RequiredArgsConstructor
public class LikeController {

    private final LikeWriteService likeWriteService;

    @PostMapping
    public Response<LikeResponseDto> toggleLike(
        @PathVariable Long feedId,
        @SessionAttribute(LOGIN_USER) Long sessionUserId
    ) {
        LikeResponseDto like = likeWriteService.toggleLike(sessionUserId, feedId);
        return Response.of(like);
    }
}
