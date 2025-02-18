package com.example.newsfeed.follow.application.service;

import com.example.newsfeed.follow.entity.Follow;
import com.example.newsfeed.follow.repository.FollowRepository;
import com.example.newsfeed.global.common.exception.BaseException;
import com.example.newsfeed.global.common.exception.ErrorDetail;
import com.example.newsfeed.user.application.service.UserReadService;
import com.example.newsfeed.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.newsfeed.global.common.exception.ErrorCode.CANNOT_FOLLOW_SELF;

@Service
@RequiredArgsConstructor
public class FollowWriteService {

    private final FollowRepository followRepository;
    private final UserReadService userReadService;

    @Transactional
    public boolean toggleFollow(Long sessionUserId, Long targetUserId) {
        if (sessionUserId.equals(targetUserId)) { // 자기 자신을 팔로우 시도 하면 발생 -> 현재 500번 에러 발생
            throw new BaseException(List.of(
                new ErrorDetail(CANNOT_FOLLOW_SELF, null, CANNOT_FOLLOW_SELF.getMessage())
            ));
        }

        User follower = userReadService.findUserById(sessionUserId);
        User following = userReadService.findUserById(targetUserId);

        return followRepository.findByFollowerAndFollowing(follower, following)
            // A유저가  B유저를 팔로잉 하는 상황
            .map(existingFollow -> { // A유저 팔로잉 리스트에 이미 B가 존재한다면
                follower.decreaseFollowings(); // A의 팔로잉 수 -1
                following.decreaseFollowers(); // B의 팔로워 수 -1
                followRepository.delete(existingFollow);
                return false; // 언팔로우 처리
            })
            .orElseGet(() -> { // A유저 팔로잉 리스트에 이미 B가 존재X
                follower.increaseFollowings(); // A의 팔로잉 수 +1
                following.increaseFollowers(); // B의 팔로워 수 +1
                Follow follow = Follow.builder()
                    .follower(follower)
                    .following(following)
                    .build();
                followRepository.save(follow);
                return true; // 팔로우 처리
            });
    }

    @Transactional
    public void decreaseFollowersOfFollowedUsers(Long sessionUserId) {
        followRepository.decreaseFollowersOfFollowedUsers(sessionUserId);
    }

    @Transactional
    public void decreaseFollowingsOfFollowers(Long sessionUserId) {
        followRepository.decreaseFollowingsOfFollowers(sessionUserId);
    }
}
