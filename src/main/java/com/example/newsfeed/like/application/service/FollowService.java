package com.example.newsfeed.like.application.service;

import com.example.newsfeed.global.common.exception.ErrorDetail;
import com.example.newsfeed.like.entity.Follow;
import com.example.newsfeed.like.exception.CannotFollowSelfException;
import com.example.newsfeed.like.repository.FollowRepository;
import com.example.newsfeed.user.application.service.UserService;
import com.example.newsfeed.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.newsfeed.global.common.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;
    private final UserService userService;
    @Transactional
    public boolean toggleFollow(Long userId, Long targetUserId) {

        if(userId.equals(targetUserId)) { // 자기 자신을 팔로우 시도 하면 발생 -> 현재 500번 에러 발생
            throw new CannotFollowSelfException(List.of(
                  new ErrorDetail(CANNOT_FOLLOW_SELF,null, CANNOT_FOLLOW_SELF.getMessage())
            ));
        }

        User follower = userService.findUserById(userId);
        User following = userService.findUserById(targetUserId);

        return followRepository.findByFollowerAndFollowing(follower, following)
                // A유저가  B유저를 팔로잉 하는 상황
                .map(existingFollow -> { // A유저 팔로잉 리스트에 이미 B가 존재한다면
                    followRepository.delete(existingFollow);
                    follower.decreaseFollowings(); // A의 팔로잉 수 -1
                    following.decreaseFollowers(); // B의 팔로워 수 -1
                    return false; // 언팔로우 처리
                })
                .orElseGet(() -> { // A유저 팔로잉 리스트에 이미 B가 존재X
                    followRepository.save(new Follow(follower, following));
                    follower.increaseFollowings(); // A의 팔로잉 수 +1
                    following.increaseFollowers(); // B의 팔로워 수 +1
                    return true; // 팔로우 처리
                });
    }
    // 특정 사용자가 팔로우한 ID 목록 조회
    @Transactional(readOnly = true)
    public List<Long> getFollowingUsers(Long userId) {
        return followRepository.findFollowingIdsByUserId(userId);
    }
    // 특정 사용자를 팔로잉한 ID 목록 조회
    @Transactional(readOnly = true)
    public List<Long> getFollowerUsers(Long userId) {
        return followRepository.findFollowerIdsByUserId(userId);
    }
}
