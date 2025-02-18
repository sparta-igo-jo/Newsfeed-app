package com.example.newsfeed.like.controller;

import com.example.newsfeed.global.common.Const.SessionConst;
import com.example.newsfeed.global.response.Response;
import com.example.newsfeed.like.application.service.LikeService;
import com.example.newsfeed.like.dto.LikeResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/feeds/{feedId}/like")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    @PostMapping
    public Response<LikeResponseDto> toggleLike(
            @PathVariable Long feedId,
            @SessionAttribute(name = SessionConst.LOGIN_USER) Long sessionUserId
    ){
        LikeResponseDto like = likeService.toggleLike(sessionUserId, feedId);
        return Response.of(like);
    }
}
