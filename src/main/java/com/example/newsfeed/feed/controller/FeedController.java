package com.example.newsfeed.feed.controller;

import com.example.newsfeed.feed.application.service.FeedReadService;
import com.example.newsfeed.feed.application.service.FeedWriteService;
import com.example.newsfeed.feed.dto.request.CreateFeedRequestDto;
import com.example.newsfeed.feed.dto.request.UpdateFeedRequestDto;
import com.example.newsfeed.feed.dto.response.GetAllFeedsResponseDto;
import com.example.newsfeed.feed.dto.response.GetFeedResponseDto;
import com.example.newsfeed.global.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.*;

import static com.example.newsfeed.global.common.constant.SessionConst.LOGIN_USER;
import static org.springframework.data.domain.Sort.Direction.DESC;

@RestController
@RequestMapping("/feeds")
@RequiredArgsConstructor
public class FeedController {

    private final FeedWriteService feedWriteService;
    private final FeedReadService feedReadService;


    @PostMapping
    public Response<Long> createFeed(
        @RequestBody CreateFeedRequestDto dto,
        @SessionAttribute(LOGIN_USER) Long sessionUserId
    ) {
        Long newFeedId = feedWriteService.createFeed(sessionUserId, dto);
        return Response.of(newFeedId, "피드 생성 성공");
    }

    // 선택한 피드의 상세정보(댓글을 포함한)
    @GetMapping("/{feedId}")
    public Response<GetFeedResponseDto> getFeed(@PathVariable Long feedId) {
        GetFeedResponseDto feed = feedReadService.getFeed(feedId);
        return Response.of(feed, "피드 단건 조회 성공");
    }

    // 나와 내가 팔로우한 사람들의 피드 목록 조회
    @GetMapping
    public Response<Page<GetAllFeedsResponseDto>> getMyFeedsWithFollowing(
        @SortDefault(sort = "updateAt", direction = DESC) Pageable pageable,
        @SessionAttribute(LOGIN_USER) Long sessionUserId
    ) {
        Page<GetAllFeedsResponseDto> feeds = feedReadService.getMyFeedsWithFollowing(sessionUserId, pageable);
        return Response.of(feeds, "피드 조회 성공");
    }

    // 내가 선택한 사람의 피드 목록 조회
    @GetMapping("/user/{userId}")
    public Response<Page<GetAllFeedsResponseDto>> getUserFeeds(
        @PathVariable Long userId,
        @SortDefault(sort = "updateAt", direction = DESC) Pageable pageable
    ) {
        Page<GetAllFeedsResponseDto> feeds = feedReadService.getUserFeeds(userId, pageable);
        return Response.of(feeds, "선택한 유저의 피드 목록 조회 성공");
    }

    @PatchMapping("/{feedId}")
    public Response<Long> updateFeed(
        @PathVariable Long feedId,
        @RequestBody UpdateFeedRequestDto dto,
        @SessionAttribute(LOGIN_USER) Long sessionUserId
    ) {
        Long updatedFeedId = feedWriteService.updateFeed(sessionUserId, feedId, dto);
        return Response.of(updatedFeedId, "피드 수정 성공");
    }

    @DeleteMapping("/{feedId}")
    public Response<Void> deleteFeed(
        @PathVariable Long feedId,
        @SessionAttribute(LOGIN_USER) Long sessionUserId
    ) {
        feedWriteService.deleteFeed(sessionUserId, feedId);
        return Response.empty("피드 삭제 성공");
    }


}
