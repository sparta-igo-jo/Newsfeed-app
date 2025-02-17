package com.example.newsfeed.like.controller;

import com.example.newsfeed.global.response.Response;
import com.example.newsfeed.like.application.service.FollowService;
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
            @PathVariable Long userId,
            @PathVariable Long targetUserId
    ) {
        boolean isFollowing = followService.toggleFollow(userId, targetUserId);
        return Response.of(isFollowing ? "팔로우" : "언팔로우");
    }

    @GetMapping("followings/count")
    public Response<List<Long>> getFollowingIds(@PathVariable Long userId) {
        List<Long> followingIds = followService.getFollowingUsers(userId);
        return Response.of(followingIds);
    }

    @GetMapping("followers/count")
    public Response<List<Long>> getFollowerIds(@PathVariable Long userId) {
        List<Long> followingIds = followService.getFollowerUsers(userId);
        return Response.of(followingIds);
    }
}