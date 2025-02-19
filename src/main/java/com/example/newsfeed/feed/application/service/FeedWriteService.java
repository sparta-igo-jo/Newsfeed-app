package com.example.newsfeed.feed.application.service;

import com.example.newsfeed.feed.dto.request.CreateFeedRequestDto;
import com.example.newsfeed.feed.dto.request.UpdateFeedRequestDto;
import com.example.newsfeed.feed.entity.Feed;
import com.example.newsfeed.feed.repository.FeedRepository;
import com.example.newsfeed.global.common.exception.BaseException;
import com.example.newsfeed.global.common.exception.ErrorDetail;
import com.example.newsfeed.user.application.service.UserReadService;
import com.example.newsfeed.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.newsfeed.global.common.exception.ErrorCode.FEED_ACCESS_DENIED;
import static com.example.newsfeed.global.common.exception.ErrorCode.FEED_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class FeedWriteService {

    private final FeedRepository feedRepository;
    private final UserReadService userReadService;

    @Transactional
    public Long createFeed(Long sessionUserId, CreateFeedRequestDto dto) {
        User findUser = userReadService.findUserById(sessionUserId);
        Feed newFeed = Feed.builder()
            .title(dto.getTitle())
            .contents(dto.getContents())
            .user(findUser)
            .feedImage(dto.getFeedImage())
            .build();
        feedRepository.save(newFeed);
        return newFeed.getId();
    }

    @Transactional
    public Long updateFeed(Long sessionUserId, Long feedId, UpdateFeedRequestDto dto) {
        Feed findFeed = findFeedByIdOrThrow(feedId);
        checkPermission(findFeed.getUser().getId(), sessionUserId);


        if (!findFeed.getTitle().equals(dto.getTitle())) {
            findFeed.updateTitle(dto.getTitle());
        }

        if (dto.getContents() != null) {
            findFeed.updateContents(dto.getContents());
        }

        if (dto.getFeedImage() != null) {
            findFeed.updateFeedImage(dto.getFeedImage());
        }

        return findFeed.getId();
    }

    @Transactional
    public void deleteFeed(Long sessionUserId, Long feedId) {
        Feed findFeed = findFeedByIdOrThrow(feedId);
        checkPermission(findFeed.getUser().getId(), sessionUserId);
        feedRepository.delete(findFeed);
    }

    public void deleteFeedsByUserId(Long userId) {
        feedRepository.deleteByUserId(userId);
    }

    private void checkPermission(Long userId, Long sessionUserId) {
        User userById = userReadService.findUserById(sessionUserId);
        if (!userId.equals(sessionUserId)) {
            throw new BaseException(List.of(
                new ErrorDetail(FEED_ACCESS_DENIED, null, FEED_ACCESS_DENIED.getMessage())
            ));
        }
    }

    private Feed findFeedByIdOrThrow(Long feedId) {
        return feedRepository.findById(feedId)
            .orElseThrow(() -> new BaseException(List.of(
                new ErrorDetail(FEED_NOT_FOUND, null, FEED_NOT_FOUND.getMessage())
            )));
    }
}
