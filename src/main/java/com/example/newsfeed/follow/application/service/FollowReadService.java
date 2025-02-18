package com.example.newsfeed.follow.application.service;

import com.example.newsfeed.follow.repository.FollowRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowReadService {

    private final FollowRepository followRepository;

    // 특정 사용자가 팔로우한 ID 목록 조회
    @Transactional(readOnly = true)
    public List<Long> findFollowingIdsByUserId(Long userId) {
        return followRepository.findFollowingIdsByUserId(userId);
    }

    // 특정 사용자를 팔로잉한 ID 목록 조회
    @Transactional(readOnly = true)
    public List<Long> findFollowerIdsByUserId(Long userId) {
        return followRepository.findFollowerIdsByUserId(userId);
    }

}
