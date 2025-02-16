package com.example.newsfeed.feed.application.service;

import com.example.newsfeed.feed.application.converter.FeedConverter;
import com.example.newsfeed.feed.dto.request.CreateFeedRequestDto;
import com.example.newsfeed.feed.dto.request.UpdateFeedRequestDto;
import com.example.newsfeed.feed.dto.response.GetFeedResponseDto;
import com.example.newsfeed.feed.entity.Feed;
import com.example.newsfeed.feed.exception.FeedNotFoundException;
import com.example.newsfeed.feed.repository.FeedRepository;
import com.example.newsfeed.global.common.exception.ErrorDetail;
import com.example.newsfeed.global.common.service.FileStorageService;
import com.example.newsfeed.user.application.service.UserService;
import com.example.newsfeed.follow.application.service.FollowService;
import com.example.newsfeed.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.newsfeed.global.common.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class FeedService {

    private final FeedRepository feedRepository;
    private final UserService userService;
    private final FileStorageService fileStorageService;

    @Transactional
    public GetFeedResponseDto createFeed(Long userId, CreateFeedRequestDto dto) {
        User findUser = userService.findUserById(userId);
        String imageUrl = null; // TODO: 초기값은 기본이미지 경로로 하면 될듯?

        if (dto.getFeedImage() != null && !dto.getFeedImage().isEmpty()) {
            imageUrl = fileStorageService.storeFile(dto.getFeedImage(), findUser.getEmail());
        }

        Feed createFeed = Feed.builder()
                .title(dto.getTile())
                .contents(dto.getContents())
                .user(findUser)
                .feedImage(imageUrl)
                .build();

        feedRepository.save(createFeed);

        return FeedConverter.toResponse(createFeed);
    }

    @Transactional(readOnly = true)
    public GetFeedResponseDto getFeed(Long feedId) {
        Feed findFeed = findFeedById(feedId);
        return FeedConverter.toResponse(findFeed);
    }

    @Transactional(readOnly = true)
    public Page<GetFeedResponseDto> getFeeds(Long userId, Pageable pageable) {
        //TODO:FollowService 에서 getFollowingIds 매서드 생성 필요
        List<Long> followingIds = followService.getFollowingIds(userId);
        followingIds.add(userId);
        Page<Feed> feeds = feedRepository.findByUserIdIn(followingIds, pageable);
        return FeedConverter.toResponse(feeds);
    }

    @Transactional
    public Long updateFeed(Long feedId, UpdateFeedRequestDto dto) {
        Feed findFeed = findFeedById(feedId);
        // TODO : 지금 방식대로 하면, 로컬 이미지 저장소에 일단 저장 한 후 비교검증하게됨.
        // TODO : 비교검증하여 이미지 삭제하던가 하는 로직 필요
        String imageUrl = fileStorageService.storeFile(dto.getFeedImage(), findFeed.getUser().getEmail());

        // ✅ 새로운 이미지가 있으면 저장 (같으면 기존 URL 유지)
        if (dto.getFeedImage() != null && !dto.getFeedImage().isEmpty()) {
            String newImageUrl = fileStorageService.storeFile(dto.getFeedImage(), findFeed.getUser().getEmail());
            if (!newImageUrl.equals(findFeed.getFeedImage())) {
                imageUrl = newImageUrl; // 새로운 이미지가 기존과 다를 경우 업데이트
            }
        }

        if (findFeed.getTitle().equals(dto.getTile())
                && findFeed.getContents().equals(dto.getContents())
                && findFeed.getFeedImage().equals(imageUrl)) {
            return findFeed.getId();
        }

        findFeed.updateTitle(dto.getTile());
        findFeed.updateContents(dto.getContents());
        findFeed.updateFeedImage(imageUrl);

        return findFeed.getId();
    }

    @Transactional
    public void deleteFeed(Long feedId) {
        Feed findFeed = findFeedById(feedId);
        feedRepository.delete(findFeed);
    }

    public Feed findFeedById(Long feedId) {
        return feedRepository.findById(feedId)
                .orElseThrow(() -> new FeedNotFoundException(List.of(
                        new ErrorDetail(FEED_NOT_FOUND, null, FEED_NOT_FOUND.getMessage())
                )));
    }
}
