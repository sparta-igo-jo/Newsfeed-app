package com.example.newsfeed.feed.controller;

import com.example.newsfeed.feed.application.service.FeedService;
import com.example.newsfeed.feed.dto.request.CreateFeedRequestDto;
import com.example.newsfeed.feed.dto.request.UpdateFeedRequestDto;
import com.example.newsfeed.feed.dto.response.CreateFeedResponseDto;
import com.example.newsfeed.feed.dto.response.GetAllFeedsResponseDto;
import com.example.newsfeed.feed.dto.response.GetFeedResponseDto;
import com.example.newsfeed.global.response.Response;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/feeds")
@RequiredArgsConstructor
public class FeedController {

    private final FeedService feedService;

    @PostMapping
    public Response<Long> createFeed(
            @RequestParam Long userId,
            @RequestBody CreateFeedRequestDto dto
    ) {
        Long createdFeedId = feedService.createFeed(userId, dto);
        return Response.of(createdFeedId);
    }

    @GetMapping("/user/{userId}")
    public Response<GetFeedResponseDto> getFeed(
            @PathVariable Long feedId,
            @PageableDefault(page = 0, size = 10, sort = "updateAt", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        GetFeedResponseDto getFeedDto = feedService.getFeed(feedId, pageable);
        return Response.of(getFeedDto);
    }

    // 나와 내가 팔로우한 사람들의 피드 조회
    //TODO: 사용자 인증 후, HttpServletRequest 객체에서 userId를 가져오는 식으로 변경?
    @GetMapping("/main/{userId}")
    public Response<Page<GetAllFeedsResponseDto>> getFeeds(
            @PathVariable Long userId,
            @PageableDefault(page = 0, size = 10, sort = "updateAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<GetAllFeedsResponseDto> getFeedsDto = feedService.getFeeds(userId, pageable);
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
