package com.example.newsfeed.user.application.service;

import com.example.newsfeed.global.common.exception.BaseException;
import com.example.newsfeed.global.common.exception.ErrorDetail;
import com.example.newsfeed.user.application.converter.UserConverter;
import com.example.newsfeed.user.dto.request.SearchUserRequestDto;
import com.example.newsfeed.user.dto.response.GetAllUsersResponseDto;
import com.example.newsfeed.user.dto.response.GetUserResponseDto;
import com.example.newsfeed.user.entity.User;
import com.example.newsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.newsfeed.global.common.exception.ErrorCode.USER_NOT_FOUND;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserReadService {

    private final UserRepository userRepository;

    // 유저 단건 조회
    public GetUserResponseDto findUser(Long userId) {
        User findUser = findUserById(userId);
        return UserConverter.toResponse(findUser);
    }

    // 키워드로 유저 전체 조회
    public Page<GetAllUsersResponseDto> findUsers(SearchUserRequestDto dto, Pageable pageable) {
        Page<User> users = userRepository.findAllUsersByKeyword(dto.getKeyword(), pageable);
        return UserConverter.toResponse(users);
    }

    public User findUserById(Long userId) {
        return userRepository.findUserById(userId)
            .orElseThrow(() -> new BaseException(List.of(
                new ErrorDetail(USER_NOT_FOUND, null, USER_NOT_FOUND.getMessage())
            )));
    }
}
