package com.example.newsfeed.feed.application.service;

import com.example.newsfeed.feed.dto.request.CreateFeedRequestDto;
import com.example.newsfeed.feed.dto.response.CreateFeedResponseDto;
import com.example.newsfeed.feed.repository.FeedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeedService {

    private final FeedRepository feedRepository;
    private final UserService userService;


    public CreateFeedResponseDto createFeed(Long userId, CreateFeedRequestDto dto) {
        User findByIdUser = userService.findByIdUser(userId);

        return null;
    }
}
