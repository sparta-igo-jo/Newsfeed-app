package com.example.newsfeed.follow.controller;

import com.example.newsfeed.follow.application.service.FollowReadService;
import com.example.newsfeed.follow.application.service.FollowWriteService;
import com.example.newsfeed.global.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.newsfeed.global.common.constant.SessionConst.LOGIN_USER;

@RestController
@RequestMapping("/users/{userId}")
@RequiredArgsConstructor
public class FollowController {

    private final FollowWriteService followWriteService;
    private final FollowReadService followReadService;

    @PostMapping("follows/{targetUserId}")
    public Response<Boolean> follow(
        @SessionAttribute(LOGIN_USER) Long sessionUserId,
        @PathVariable Long targetUserId
    ) {
        boolean isFollowing = followWriteService.toggleFollow(sessionUserId, targetUserId);
        return Response.of(isFollowing);
    }

    @GetMapping("followings/count")
    public Response<List<Long>> findFollowingIdsByUserId(@PathVariable Long userId) {
        List<Long> followingIds = followReadService.findFollowingIdsByUserId(userId);
        return Response.of(followingIds);
    }

    @GetMapping("followers/count")
    public Response<List<Long>> findFollowerIdsByUserId(@PathVariable Long userId) {
        List<Long> followingIds = followReadService.findFollowerIdsByUserId(userId);
        return Response.of(followingIds);
    }
}