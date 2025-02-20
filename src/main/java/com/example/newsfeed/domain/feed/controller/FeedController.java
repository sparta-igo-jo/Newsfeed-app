package com.example.newsfeed.domain.feed.controller;

import com.example.newsfeed.domain.feed.application.service.FeedReadService;
import com.example.newsfeed.domain.feed.application.service.FeedWriteService;
import com.example.newsfeed.domain.feed.dto.request.CreateFeedRequestDto;
import com.example.newsfeed.domain.feed.dto.request.UpdateFeedRequestDto;
import com.example.newsfeed.domain.feed.dto.response.GetAllFeedsResponseDto;
import com.example.newsfeed.domain.feed.dto.response.GetFeedResponseDto;
import com.example.newsfeed.common.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.*;

import static com.example.newsfeed.common.constant.SessionConst.LOGIN_USER;
import static org.springframework.data.domain.Sort.Direction.DESC;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class FeedController {

    private final FeedWriteService feedWriteService;
    private final FeedReadService feedReadService;


    @PostMapping("/feeds")
    public Response<Long> createFeed(
        @RequestBody CreateFeedRequestDto dto,
        @SessionAttribute(LOGIN_USER) Long sessionUserId
    ) {
        Long newFeedId = feedWriteService.createFeed(sessionUserId, dto);
        return Response.of(newFeedId, "피드 생성 성공");
    }

    // 선택한 피드의 상세정보(댓글을 포함한)
    @GetMapping("/feeds/{feedId}")
    public Response<GetFeedResponseDto> getFeed(@PathVariable Long feedId) {
        GetFeedResponseDto feed = feedReadService.getFeed(feedId);
        return Response.of(feed, "피드 단건 조회 성공");
    }

    // 나와 내가 팔로우한 사람들의 피드 목록 조회
    @GetMapping("/feeds")
    public Response<Page<GetAllFeedsResponseDto>> getMyFeedsWithFollowing(
        @SortDefault(sort = "updatedAt", direction = DESC) Pageable pageable,
        @SessionAttribute(LOGIN_USER) Long sessionUserId
    ) {
        Page<GetAllFeedsResponseDto> feeds = feedReadService.getMyFeedsWithFollowing(sessionUserId, pageable);
        return Response.of(feeds, "피드 조회 성공");
    }

    // 내가 선택한 사람의 피드 목록 조회
    @GetMapping("/feeds/users/{userId}")
    public Response<Page<GetAllFeedsResponseDto>> getUserFeeds(
        @PathVariable Long userId,
        @SortDefault(sort = "updatedAt", direction = DESC) Pageable pageable
    ) {
        Page<GetAllFeedsResponseDto> feeds = feedReadService.getUserFeeds(userId, pageable);
        return Response.of(feeds, "선택한 유저의 피드 목록 조회 성공");
    }

    @PatchMapping("/feeds/{feedId}")
    public Response<Long> updateFeed(
        @PathVariable Long feedId,
        @RequestBody UpdateFeedRequestDto dto,
        @SessionAttribute(LOGIN_USER) Long sessionUserId
    ) {
        Long updatedFeedId = feedWriteService.updateFeed(sessionUserId, feedId, dto);
        return Response.of(updatedFeedId, "피드 수정 성공");
    }

    @DeleteMapping("/feeds/{feedId}")
    public Response<Void> deleteFeed(
        @PathVariable Long feedId,
        @SessionAttribute(LOGIN_USER) Long sessionUserId
    ) {
        feedWriteService.deleteFeed(sessionUserId, feedId);
        return Response.empty("피드 삭제 성공");
    }


}
