package com.example.newsfeed.feed.controller;

import com.example.newsfeed.feed.application.service.FeedService;
import com.example.newsfeed.feed.dto.request.CreateFeedRequestDto;
import com.example.newsfeed.feed.dto.request.UpdateFeedRequestDto;
import com.example.newsfeed.feed.dto.response.GetAllFeedsResponseDto;
import com.example.newsfeed.feed.dto.response.GetFeedResponseDto;
import com.example.newsfeed.global.common.Const.SessionConst;
import com.example.newsfeed.global.response.Response;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/feeds")
@RequiredArgsConstructor
public class FeedController {

    private final FeedService feedService;

    @PostMapping
    public Response<Long> createFeed(
            @RequestBody CreateFeedRequestDto dto,
            HttpSession session
    ) {
        Long sessionUserId = (Long) session.getAttribute(SessionConst.LOGIN_USER);
        Long createdFeedId = feedService.createFeed(sessionUserId, dto);
        return Response.of(createdFeedId);
    }

    // 선택한 피드의 상세정보(댓글을 포함한)
    @GetMapping("/{feedId}")
    public Response<GetFeedResponseDto> getFeed(
            @PathVariable Long feedId,
            @PageableDefault(page = 0, size = 10, sort = "updateAt", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        GetFeedResponseDto feed = feedService.getFeed(feedId, pageable);
        return Response.of(feed);
    }

    // 나와 내가 팔로우한 사람들의 피드 목록 조회
    @GetMapping
    public Response<Page<GetAllFeedsResponseDto>> getMyFeedsWithFollowing(
            @PageableDefault(page = 0, size = 10, sort = "updateAt", direction = Sort.Direction.DESC) Pageable pageable,
            HttpSession session
    ) {
        Long sessionUserId = (Long) session.getAttribute(SessionConst.LOGIN_USER);
        Page<GetAllFeedsResponseDto> myFeeds = feedService.getMyFeedsWithFollowing(sessionUserId, pageable);
        return Response.of(myFeeds);
    }

    // 내가 선택한 사람의 피드 목록 조회
    @GetMapping("/user/{userId}")
    public Response<Page<GetAllFeedsResponseDto>> getUserFeeds(
            @PathVariable Long userId,
            @PageableDefault(page = 0, size = 10, sort = "updateAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<GetAllFeedsResponseDto> userFeeds = feedService.getUserFeeds(userId, pageable);
        return Response.of(userFeeds);
    }

    @PatchMapping("/{feedId}")
    public Response<Long> updateFeed(
            @PathVariable Long feedId,
            @RequestBody UpdateFeedRequestDto dto,
            HttpSession session
    ) {
        Long sessionUserId = (Long) session.getAttribute(SessionConst.LOGIN_USER);
        Long updatedFeedId = feedService.updateFeed(sessionUserId, feedId, dto);
        return Response.of(updatedFeedId);
    }

    @DeleteMapping("/{feedId}")
    public Response<Void> deleteFeed(
            @PathVariable Long feedId,
            HttpSession session
    ) {
        Long sessionUserId = (Long) session.getAttribute(SessionConst.LOGIN_USER);
        feedService.deleteFeed(sessionUserId, feedId);
        return Response.empty();
    }


}
