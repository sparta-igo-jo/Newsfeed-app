package com.example.newsfeed.like.application.service;

import com.example.newsfeed.feed.application.service.FeedService;
import com.example.newsfeed.feed.entity.Feed;
import com.example.newsfeed.like.dto.LikeResponseDto;
import com.example.newsfeed.like.entity.Like;
import com.example.newsfeed.like.repository.LikeRepository;
import com.example.newsfeed.user.application.service.UserService;
import com.example.newsfeed.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final FeedService feedService;
    private final UserService userService;

    @Transactional
    public LikeResponseDto toggleLike(Long sessionUserId, Long feedId){
        Feed feed = feedService.findFeedByFeedId(feedId);
        User user = userService.findUserById(sessionUserId);
        return likeRepository.findByUserAndFeed(user, feed)
                .map(existringLike -> {
                    feed.decreaseLikeCount();
                    likeRepository.delete(existringLike);
                    return new LikeResponseDto(feedId, false, feed.getLikeCount());
                })
                .orElseGet(() -> {
                    Like like = Like.builder()
                            .user(user)
                            .feed(feed)
                            .build();
                    feed.increaseLikeCount();
                    likeRepository.save(like);
                    return new LikeResponseDto(feedId, true, feed.getLikeCount());
                });
    }
}
