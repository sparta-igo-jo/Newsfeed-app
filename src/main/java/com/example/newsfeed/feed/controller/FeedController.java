package com.example.newsfeed.feed.controller;

import com.example.newsfeed.feed.application.service.FeedService;
import com.example.newsfeed.feed.dto.request.CreateFeedRequestDto;
import com.example.newsfeed.feed.dto.request.UpdateFeedRequestDto;
import com.example.newsfeed.feed.dto.response.GetFeedResponseDto;
import com.example.newsfeed.global.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/feeds")
@RequiredArgsConstructor
public class FeedController {

    private final FeedService feedService;

    @PostMapping
    public Response<GetFeedResponseDto> createFeed(
            @RequestParam Long userId,
            @RequestBody CreateFeedRequestDto dto
    ) {
        GetFeedResponseDto getFeedDto = feedService.createFeed(userId, dto);
        return Response.of(getFeedDto);
    }

    @GetMapping
    public Response<GetFeedResponseDto> getFeed(@RequestParam Long feedId) {
        GetFeedResponseDto getFeedDto = feedService.getFeed(feedId);
        return Response.of(getFeedDto);
    }

    // 나와 내가 팔로우한 사람들의 피드 조회
    // 실제 요청 URL == /feeds?userId=1&page=1&size=10&sort=updatedAt,desc (내림차순, 최신순정렬)
    @GetMapping
    public Response<Page<GetFeedResponseDto>> getFeeds(@RequestParam Long userId, Pageable pageable) {
        Page<GetFeedResponseDto> getFeedsDto = feedService.getFeeds(userId, pageable);
        return Response.of(getFeedsDto);
    }

    @PatchMapping("/{feedId}")
    public Response<Long> updateFeed(
            @PathVariable Long feedId,
            @RequestBody UpdateFeedRequestDto dto
    ) {
        Long updatedFeedId = feedService.updateFeed(feedId, dto);
        return Response.of(updatedFeedId);
    }

    @DeleteMapping("/{feedId}")
    public Response<Void> deleteFeed(@PathVariable Long feedId) {
        feedService.deleteFeed(feedId);
        return Response.empty();
    }


}
