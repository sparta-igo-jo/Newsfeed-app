package com.example.newsfeed.feed.controller;

import com.example.newsfeed.feed.application.service.FeedService;
import com.example.newsfeed.feed.dto.request.CreateFeedRequestDto;
import com.example.newsfeed.feed.dto.request.UpdateFeedRequestDto;
import com.example.newsfeed.feed.dto.response.GetAllFeedsResponseDto;
import com.example.newsfeed.feed.dto.response.GetFeedResponseDto;
import com.example.newsfeed.global.common.constant.SessionConst;
import com.example.newsfeed.global.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.*;

import static com.example.newsfeed.global.common.constant.SessionConst.LOGIN_USER;

@RestController
@RequestMapping("/feeds")
@RequiredArgsConstructor
public class FeedController {

    private final FeedService feedService;

    @PostMapping
    public Response<Long> createFeed(
            @RequestBody CreateFeedRequestDto dto,
            @SessionAttribute(name = LOGIN_USER) Long sessionUserId
    ) {
        Long createdFeedId = feedService.createFeed(sessionUserId, dto);
        return Response.of(createdFeedId, "피드가 생성되었습니다.");
    }

    // 선택한 피드의 상세정보(댓글을 포함한)
    @GetMapping("/{feedId}")
    public Response<GetFeedResponseDto> getFeed(
            @PathVariable Long feedId
    ) {
        GetFeedResponseDto feed = feedService.getFeed(feedId);
        return Response.of(feed, "피드가 조회되었습니다.");
    }

    // 나와 내가 팔로우한 사람들의 피드 목록 조회
    @GetMapping
    public Response<Page<GetAllFeedsResponseDto>> getMyFeedsWithFollowing(
            @SortDefault(sort = "updateAt", direction = Sort.Direction.DESC) Pageable pageable,
            @SessionAttribute(name = LOGIN_USER) Long sessionUserId
    ) {
        Page<GetAllFeedsResponseDto> myFeeds = feedService.getMyFeedsWithFollowing(sessionUserId, pageable);
        return Response.of(myFeeds, "피드가 조회되었습니다.");
    }

    // 내가 선택한 사람의 피드 목록 조회
    @GetMapping("/user/{userId}")
    public Response<Page<GetAllFeedsResponseDto>> getUserFeeds(
            @PathVariable Long userId,
            @SortDefault(sort = "updateAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<GetAllFeedsResponseDto> userFeeds = feedService.getUserFeeds(userId, pageable);
        return Response.of(userFeeds, "피드가 조회되었습니다.");
    }

    @PatchMapping("/{feedId}")
    public Response<Long> updateFeed(
            @PathVariable Long feedId,
            @RequestBody UpdateFeedRequestDto dto,
            @SessionAttribute(name = LOGIN_USER) Long sessionUserId
    ) {
        Long updatedFeedId = feedService.updateFeed(sessionUserId, feedId, dto);
        return Response.of(updatedFeedId, "피드가 수정되었습니다.");
    }

    @DeleteMapping("/{feedId}")
    public Response<Void> deleteFeed(
            @PathVariable Long feedId,
            @SessionAttribute(name = LOGIN_USER) Long sessionUserId
    ) {
        feedService.deleteFeed(sessionUserId, feedId);
        return Response.empty("피드가 삭제되었습니다.");
    }


}
