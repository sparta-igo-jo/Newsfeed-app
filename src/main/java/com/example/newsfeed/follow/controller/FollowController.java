package com.example.newsfeed.follow.controller;

import com.example.newsfeed.follow.application.service.FollowService;
import com.example.newsfeed.global.common.constant.SessionConst;
import com.example.newsfeed.global.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}")
@RequiredArgsConstructor
public class FollowController {
    private final FollowService followService;

    @PostMapping("follows/{targetUserId}")
    public Response<String> follow(
            @SessionAttribute(name = SessionConst.LOGIN_USER) Long sessionUserId,
            @PathVariable Long targetUserId
    ) {
        boolean isFollowing = followService.toggleFollow(sessionUserId, targetUserId);
        return Response.of(isFollowing ? "팔로우" : "언팔로우");
    }

    @GetMapping("followings/count")
    public Response<List<Long>> findFollowingIdsByUserId(@PathVariable Long userId) {
        List<Long> followingIds = followService.findFollowingIdsByUserId(userId);
        return Response.of(followingIds);
    }

    @GetMapping("followers/count")
    public Response<List<Long>> findFollowerIdsByUserId(@PathVariable Long userId) {
        List<Long> followingIds = followService.findFollowerIdsByUserId(userId);
        return Response.of(followingIds);
    }
}