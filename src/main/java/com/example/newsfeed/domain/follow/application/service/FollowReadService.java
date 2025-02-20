package com.example.newsfeed.domain.follow.application.service;

import com.example.newsfeed.domain.follow.application.converter.FollowConverter;
import com.example.newsfeed.domain.follow.dto.response.GetFollowResponseDto;
import com.example.newsfeed.domain.follow.entity.Follow;
import com.example.newsfeed.domain.follow.repository.FollowRepository;
import com.example.newsfeed.domain.user.application.service.UserReadService;
import com.example.newsfeed.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FollowReadService {

    private final FollowRepository followRepository;
    private final UserReadService userReadService;

    // 특정 사용자가 팔로우한 ID 목록 조회
    public List<Long> findFollowingIdsByUserId(Long userId) {
        return followRepository.findFollowingIdsByUserId(userId);
    }

    // 특정 사용자를 팔로잉한 ID 목록 조회
    public List<Long> findFollowerIdsByUserId(Long userId) {
        return followRepository.findFollowerIdsByUserId(userId);
    }

    // targetUser의 팔로워 목록을 조회
    public Page<GetFollowResponseDto> getTargetUserFollowers(
        Long targetUserId,
        Long sessionUserId,
        Pageable pageable
    ) {
        return getFollowData(targetUserId, sessionUserId, pageable, true);
    }

    // targetUser가 팔로우 중인 사용자 목록을 조회
    public Page<GetFollowResponseDto> getTargetUserFollowings(
        Long targetUserId,
        Long sessionUserId,
        Pageable pageable
    ) {
        return getFollowData(targetUserId, sessionUserId, pageable, false);
    }

    private Page<GetFollowResponseDto> getFollowData(
        Long targetUserId,
        Long sessionUserId,
        Pageable pageable,
        boolean isFollowerQuery
    ) {
        User targetUser = userReadService.findUserById(targetUserId);

        // Follow 데이터를 페이징 처리하여 조회 (팔로워/팔로잉 여부에 따라 다른 쿼리 실행)
        Page<Follow> followRecordPage = isFollowerQuery
            ? followRepository.findByFollowing(targetUser, pageable)
            : followRepository.findByFollower(targetUser, pageable);

        // 세션 사용자의 모든 팔로우 관계 미리 조회
        Set<Long> followingIds = new HashSet<>(followRepository.findFollowingIdsByUserId(sessionUserId));

        return followRecordPage.map(follow -> {
            User relatedUser = isFollowerQuery ? follow.getFollower() : follow.getFollowing();
            boolean isFollowing = followingIds.contains(relatedUser.getId());
            return FollowConverter.toResponse(relatedUser, isFollowing);
        });
    }
}
