package com.example.newsfeed.feed.application.service;

import com.example.newsfeed.feed.application.converter.FeedConverter;
import com.example.newsfeed.feed.dto.request.CreateFeedRequestDto;
import com.example.newsfeed.feed.dto.request.UpdateFeedRequestDto;
import com.example.newsfeed.feed.dto.response.GetAllFeedsResponseDto;
import com.example.newsfeed.feed.dto.response.GetFeedResponseDto;
import com.example.newsfeed.feed.entity.Feed;
import com.example.newsfeed.feed.exception.FeedNotFoundException;
import com.example.newsfeed.feed.exception.FeedUpdateFailedException;
import com.example.newsfeed.feed.repository.FeedRepository;
import com.example.newsfeed.global.common.exception.ErrorDetail;
import com.example.newsfeed.user.application.service.UserService;
import com.example.newsfeed.follow.application.service.FollowService;
import com.example.newsfeed.comment.application.service.CommentService;
import com.example.newsfeed.user.entity.User;
import com.example.newsfeed.user.entity.Comment;
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
    private final FollowService followService;
    private final CommentService commentService;

    @Transactional
    public Long createFeed(Long sessionUserId, CreateFeedRequestDto dto) {
        User findUser = userService.findUserById(sessionUserId);
        //TODO: 파일 저장 및 로드는 완성되면 추가해야 함
        Feed createFeed = Feed.builder()
                .title(dto.getTile())
                .contents(dto.getContents())
                .user(findUser)
                .build();
        feedRepository.save(createFeed);
        return createFeed.getId();
    }

    @Transactional(readOnly = true)
    public GetFeedResponseDto getFeed(Long feedId, Pageable pageable) {
        Feed findFeed = findFeedByFeedId(feedId);

        //TODO:CommentService 에서 findCommentsByFeedId 매서드 생성 필요
        Page<Comment> comments = commentService.findCommentsByFeedId(feedId, pageable);
        return FeedConverter.toResponse(findFeed, comments);
    }

     //나와 내가 팔로우한 사람들의 피드 목록 조회
    @Transactional(readOnly = true)
    public Page<GetAllFeedsResponseDto> getMyFeedsWithFollowing(Long sessionUserId, Pageable pageable) {

        //TODO:FollowService 에서 findFollowingIdsByUserId 매서드 생성 필요
        List<Long> followingIds = followService.findFollowingIdsByUserId(sessionUserId);
        followingIds.add(sessionUserId);
        Page<Feed> feeds = feedRepository.findByUserIdIn(followingIds, pageable);
        return FeedConverter.toResponse(feeds);
    }

    // 내가 선택한 사람의 피드 목록 조회
    @Transactional(readOnly = true)
    public Page<GetAllFeedsResponseDto> getUserFeeds(Long userId, Pageable pageable) {
        User findUser = userService.findUserById(userId);
        Page<Feed> feeds = feedRepository.findByUser(findUser, pageable);
        return FeedConverter.toResponse(feeds);
    }

    @Transactional
    public Long updateFeed(Long sessionUserId, Long feedId, UpdateFeedRequestDto dto) {
        Feed findFeed = getFeedAfterAuthorization(sessionUserId, feedId);

        //TODO: 파일 저장 및 로드는 완성되면 추가해야 함
        if (findFeed.getTitle().equals(dto.getTile())
                && findFeed.getContents().equals(dto.getContents())
        ) {
            return findFeed.getId();
        }

        findFeed.updateTitle(dto.getTile());
        findFeed.updateContents(dto.getContents());

        return findFeed.getId();
    }

    @Transactional
    public void deleteFeed(Long sessionUserId, Long feedId) {
        Feed findFeed = getFeedAfterAuthorization(sessionUserId, feedId);
        feedRepository.delete(findFeed);
    }

    private Feed getFeedAfterAuthorization(Long sessionUserId, Long feedId) {
        User findUser = userService.findUserById(sessionUserId);
        Feed findFeed = findFeedByFeedId(feedId);
        if (!findFeed.getUser().equals(findUser)) {
            throw new FeedUpdateFailedException(List.of(
                    new ErrorDetail(NO_PERMISSION_AT_UPDATE_FEED, null, NO_PERMISSION_AT_UPDATE_FEED.getMessage())
            ));
        }
        return findFeed;
    }

    public Feed findFeedByFeedId(Long feedId) {
        return feedRepository.findById(feedId)
            .orElseThrow(() -> new FeedNotFoundException(List.of(
                    new ErrorDetail(FEED_NOT_FOUND, null, FEED_NOT_FOUND.getMessage())
                )));
    }
}
