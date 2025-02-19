package com.example.newsfeed.follow.controller;

import com.example.newsfeed.follow.application.service.FollowReadService;
import com.example.newsfeed.follow.application.service.FollowWriteService;
import com.example.newsfeed.follow.dto.response.GetFollowResponseDto;
import com.example.newsfeed.global.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.newsfeed.global.common.constant.SessionConst.LOGIN_USER;
import static org.springframework.data.domain.Sort.Direction.ASC;

@RestController
@RequestMapping("/follows")
@RequiredArgsConstructor
public class FollowController {

    private final FollowWriteService followWriteService;
    private final FollowReadService followReadService;

    @PostMapping("/{userId}")
    public Response<Boolean> follow(
        @SessionAttribute(LOGIN_USER) Long sessionUserId,
        @PathVariable Long userId
    ) {
        boolean isFollowing = followWriteService.toggleFollow(sessionUserId, userId);
        return Response.of(isFollowing, isFollowing ? "팔로우 성공" : "언팔로우 성공");
    }

    @GetMapping("/{userId}/followers/count")
    public Response<List<Long>> findFollowerIdsByUserId(@PathVariable Long userId) {
        List<Long> followingIds = followReadService.findFollowerIdsByUserId(userId);
        return Response.of(followingIds, "선택한 유저의 팔로워 인원 수 조회 성공");
    }

    @GetMapping("/{userId}/followings/count")
    public Response<List<Long>> findFollowingIdsByUserId(@PathVariable Long userId) {
        List<Long> followingIds = followReadService.findFollowingIdsByUserId(userId);
        return Response.of(followingIds, "선택한 유저가 팔로우한 인원 수 조회 성공 ");
    }

    @GetMapping("/{userId}/followers")
    public Response<Page<GetFollowResponseDto>> getFollowersByUser(
        @PathVariable Long userId,
        @SessionAttribute(LOGIN_USER) Long sessionUserId,
        @SortDefault(sort = "name", direction = ASC) Pageable pageable
    ) {
        Page<GetFollowResponseDto> targetUserFollowers =
            followReadService.getTargetUserFollowers(userId, sessionUserId, pageable);
        return Response.of(targetUserFollowers, "선택한 유저의 팔로워 목록 조회 성공");
    }

    @GetMapping("/{userId}/followings")
    public Response<Page<GetFollowResponseDto>> getFollowingsByUser(
        @PathVariable Long userId,
        @SessionAttribute(LOGIN_USER) Long sessionUserId,
        @SortDefault(sort = "name", direction = ASC) Pageable pageable
    ) {
        Page<GetFollowResponseDto> targetUserFollowings =
            followReadService.getTargetUserFollowings(userId, sessionUserId, pageable);
        return Response.of(targetUserFollowings, "선택한 유저가 팔로우 중인 사용자 목록 조회 성공");
    }
}