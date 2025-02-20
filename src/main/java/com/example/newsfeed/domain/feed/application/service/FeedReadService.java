package com.example.newsfeed.domain.feed.application.service;

import com.example.newsfeed.domain.feed.application.converter.FeedConverter;
import com.example.newsfeed.domain.feed.dto.response.GetAllFeedsResponseDto;
import com.example.newsfeed.domain.feed.dto.response.GetFeedResponseDto;
import com.example.newsfeed.domain.feed.entity.Feed;
import com.example.newsfeed.domain.feed.repository.FeedRepository;
import com.example.newsfeed.domain.follow.application.service.FollowReadService;
import com.example.newsfeed.common.exception.BaseException;
import com.example.newsfeed.common.exception.ErrorDetail;
import com.example.newsfeed.domain.user.application.service.UserReadService;
import com.example.newsfeed.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.newsfeed.common.exception.ErrorCode.FEED_NOT_FOUND;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FeedReadService {

    private final FeedRepository feedRepository;
    private final UserReadService userReadService;
    private final FollowReadService followReadService;

    @Transactional(readOnly = true)
    public GetFeedResponseDto getFeed(Long feedId) {
        Feed findFeed = findFeedByIdOrThrow(feedId);
        return FeedConverter.toResponse(findFeed);
    }

    //나와 내가 팔로우한 사람들의 피드 목록 조회
    @Transactional(readOnly = true)
    public Page<GetAllFeedsResponseDto> getMyFeedsWithFollowing(
        Long sessionUserId,
        Pageable pageable
    ) {
        List<Long> followingIds = followReadService.findFollowingIdsByUserId(sessionUserId);
        followingIds.add(sessionUserId);
        Page<Feed> feeds = feedRepository.findByUserIdIn(followingIds, pageable);
        return FeedConverter.toResponse(feeds);
    }

    // 내가 선택한 사람의 피드 목록 조회
    @Transactional(readOnly = true)
    public Page<GetAllFeedsResponseDto> getUserFeeds(
        Long userId,
        Pageable pageable
    ) {
        User findUser = userReadService.findUserById(userId);
        Page<Feed> feeds = feedRepository.findByUser(findUser, pageable);
        return FeedConverter.toResponse(feeds);
    }

    public Feed findFeedByIdOrThrow(Long feedId) {
        return feedRepository.findById(feedId)
            .orElseThrow(() -> new BaseException(List.of(
                new ErrorDetail(FEED_NOT_FOUND, null, FEED_NOT_FOUND.getMessage())
            )));
    }
}
