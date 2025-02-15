package com.example.newsfeed.feed.controller;

import com.example.newsfeed.feed.application.service.FeedService;
import com.example.newsfeed.feed.dto.request.CreateFeedRequestDto;
import com.example.newsfeed.feed.dto.response.CreateFeedResponseDto;
import com.example.newsfeed.global.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/feeds")
@RequiredArgsConstructor
public class FeedController {

    private final FeedService feedService;

    @PostMapping("/{userId}")
    public Response<CreateFeedResponseDto> createFeed(
            @PathVariable Long userId,
            @RequestBody CreateFeedRequestDto dto
    ) {
        feedService.createFeed(userId, dto);
        return Response.of();
    }
}
