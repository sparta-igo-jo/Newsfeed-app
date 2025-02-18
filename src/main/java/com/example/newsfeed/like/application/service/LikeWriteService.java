package com.example.newsfeed.like.application.service;

import com.example.newsfeed.feed.application.service.FeedReadService;
import com.example.newsfeed.feed.entity.Feed;
import com.example.newsfeed.like.dto.LikeResponseDto;
import com.example.newsfeed.like.entity.Like;
import com.example.newsfeed.like.repository.LikeRepository;
import com.example.newsfeed.user.application.service.UserReadService;
import com.example.newsfeed.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeWriteService {

    private final LikeRepository likeRepository;
    private final FeedReadService feedReadService;
    private final UserReadService userReadService;

    @Transactional
    public LikeResponseDto toggleLike(Long sessionUserId, Long feedId) {
        Feed findFeed = feedReadService.findFeedByIdOrThrow(feedId);
        User findUser = userReadService.findUserById(sessionUserId);
        return likeRepository.findByUserAndFeed(findUser, findFeed)
            .map(existringLike -> {
                findFeed.decreaseLikeCount();
                likeRepository.delete(existringLike);
                return new LikeResponseDto(feedId, false, findFeed.getLikeCount());
            })
            .orElseGet(() -> {
                Like like = Like.builder()
                    .user(findUser)
                    .feed(findFeed)
                    .build();
                findFeed.increaseLikeCount();
                likeRepository.save(like);
                return new LikeResponseDto(feedId, true, findFeed.getLikeCount());
            });
    }

    @Transactional
    public void decreaseLikesOfFeeds(Long sessionUserId) {
        likeRepository.decreaseLikesOfFeeds(sessionUserId);
    }
}
