package com.example.newsfeed.user.application.service;

import com.example.newsfeed.comment.application.service.CommentService;
import com.example.newsfeed.feed.application.service.FeedService;
import com.example.newsfeed.follow.application.service.FollowService;
import com.example.newsfeed.global.common.exception.ErrorDetail;
import com.example.newsfeed.like.application.service.LikeService;
import com.example.newsfeed.user.application.converter.UserConverter;
import com.example.newsfeed.user.dto.request.DeleteUserRequestDto;
import com.example.newsfeed.user.dto.request.SearchUserRequestDto;
import com.example.newsfeed.user.dto.request.UpdateUserPasswordRequestDto;
import com.example.newsfeed.user.dto.request.UpdateUserRequestDto;
import com.example.newsfeed.user.dto.response.GetAllUsersResponseDto;
import com.example.newsfeed.user.dto.response.GetUserResponseDto;
import com.example.newsfeed.user.entity.User;
import com.example.newsfeed.user.exception.InvalidPasswordException;
import com.example.newsfeed.user.exception.PasswordSameAsOldException;
import com.example.newsfeed.user.exception.UserAccessDeniedException;
import com.example.newsfeed.user.exception.UserNotFoundException;
import com.example.newsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.newsfeed.global.common.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final FeedService feedService;
    private final CommentService commentService;
    private final FollowService followService;
    private final LikeService likeService;

    // 유저 단건 조회
    @Transactional(readOnly = true)
    public GetUserResponseDto findUser(Long userId) {
        User findUser = findUserById(userId);
        return UserConverter.toResponse(findUser);
    }

    // 키워드로 유저 전체 조회
    @Transactional(readOnly = true)
    public Page<GetAllUsersResponseDto> findUsers(SearchUserRequestDto dto, Pageable pageable) {
        Page<User> users = userRepository.findAllUsersByKeyword(dto.getKeyword(), pageable);
        return UserConverter.toResponse(users);
    }

    // 유저 프로필 수정
    @Transactional
    public Long updateUserProfile(
        Long userId,
        UpdateUserRequestDto dto,
        Long sessionUserId
    ) {
        checkUserPermission(userId, sessionUserId);
        User findUser = findUserById(userId);

        validatePassword(dto.getPassword(), findUser.getPassword());

        if (dto.getName() != null) {
            findUser.updateName(dto.getName());
        }

        if (dto.getDescription() != null) {
            findUser.updateDescription(dto.getDescription());
        }

        if (dto.getImagePath() != null) {
            findUser.updateProfileImage(dto.getImagePath());
        }

        return findUser.getId();
    }

    // 유저 비밀번호 수정
    @Transactional
    public Long updateUserPassword(
        Long userId,
        UpdateUserPasswordRequestDto dto,
        Long sessionUserId
    ) {
        checkUserPermission(userId, sessionUserId);
        User findUser = findUserById(userId);

        validatePassword(dto.getPassword(), findUser.getPassword());

        if (dto.getPassword().equals(dto.getChangePassword())) {
            throw new PasswordSameAsOldException(List.of(
                new ErrorDetail(PASSWORD_SAME_AS_OLD, "changePassword", PASSWORD_SAME_AS_OLD.getMessage())
            ));
        }

        findUser.updatePassword(passwordEncoder.encode(dto.getChangePassword()));
        return findUser.getId();
    }

    // 유저 탈퇴
    @Transactional
    public void deleteUser(
        Long userId,
        DeleteUserRequestDto dto,
        Long sessionUserId
    ) {
        checkUserPermission(userId, sessionUserId);
        User findUser = findUserById(userId);
        validatePassword(dto.getPassword(), findUser.getPassword());

        // 탈퇴 유저가 팔로우한 유저들의 팔로워 수를 줄임
        followService.decreaseFollowersOfFollowedUsers(findUser.getId());

        // 탈퇴 유저를 팔로워한 유저들의 팔로잉 수를 줄임
        followService.decreaseFollowingsOfFollowers(findUser.getId());

        // 탈퇴 유저가 좋아요한 피드들의 좋아요 수를 줄임
        likeService.decreaseLikesOfFeeds(sessionUserId);
        
        // 탈퇴하려는 유저와 관련된 댓글과 피드 모두 삭제
        commentService.deleteCommentsByUserId(findUser.getId());
        feedService.deleteFeedsByUserId(findUser.getId());
        userRepository.deleteUserById(userId);
    }

    public User findUserById(Long userId) {
        return userRepository.findUserById(userId)
            .orElseThrow(() -> new UserNotFoundException(List.of(
                new ErrorDetail(USER_NOT_FOUND, null, USER_NOT_FOUND.getMessage())
            )));
    }

    // 로그인한 유저의 리소스 접근 허용 여부 확인
    private void checkUserPermission(Long userId, Long sessionUserId) {
        if (!userId.equals(sessionUserId)) {
            throw new UserAccessDeniedException(List.of(
                new ErrorDetail(USER_ACCESS_DENIED, null, USER_ACCESS_DENIED.getMessage())
            ));
        }
    }

    private void validatePassword(String inputPassword, String storedPassword) {
        if (!passwordEncoder.matches(inputPassword, storedPassword)) {
            throw new InvalidPasswordException(List.of(
                new ErrorDetail(INVALID_PASSWORD, "password", INVALID_PASSWORD.getMessage())
            ));
        }
    }
}
