package com.example.newsfeed.domain.user.application.service;

import com.example.newsfeed.domain.comment.application.service.CommentWriteService;
import com.example.newsfeed.domain.feed.application.service.FeedWriteService;
import com.example.newsfeed.domain.follow.application.service.FollowWriteService;
import com.example.newsfeed.common.exception.BaseException;
import com.example.newsfeed.common.exception.ErrorDetail;
import com.example.newsfeed.domain.like.application.service.LikeWriteService;
import com.example.newsfeed.domain.user.dto.request.DeleteUserRequestDto;
import com.example.newsfeed.domain.user.dto.request.UpdateUserPasswordRequestDto;
import com.example.newsfeed.domain.user.dto.request.UpdateUserRequestDto;
import com.example.newsfeed.domain.user.entity.User;
import com.example.newsfeed.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.newsfeed.common.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class UserWriteService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final FeedWriteService feedWriteService;
    private final CommentWriteService commentWriteService;
    private final FollowWriteService followWriteService;
    private final LikeWriteService likeWriteService;

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
            throw new BaseException(List.of(
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
        followWriteService.decreaseFollowersOfFollowedUsers(findUser.getId());

        // 탈퇴 유저를 팔로워한 유저들의 팔로잉 수를 줄임
        followWriteService.decreaseFollowingsOfFollowers(findUser.getId());

        // 탈퇴 유저가 좋아요한 피드들의 좋아요 수를 줄임
        likeWriteService.decreaseLikesOfFeeds(sessionUserId);

        // 탈퇴하려는 유저와 관련된 댓글과 피드 모두 삭제
        commentWriteService.deleteCommentsByUserId(findUser.getId());
        feedWriteService.deleteFeedsByUserId(findUser.getId());
        userRepository.deleteUserById(userId);
    }

    private User findUserById(Long userId) {
        return userRepository.findUserById(userId)
            .orElseThrow(() -> new BaseException(List.of(
                new ErrorDetail(USER_NOT_FOUND, null, USER_NOT_FOUND.getMessage())
            )));
    }

    // 로그인한 유저의 리소스 접근 허용 여부 확인
    private void checkUserPermission(Long userId, Long sessionUserId) {
        if (!userId.equals(sessionUserId)) {
            throw new BaseException(List.of(
                new ErrorDetail(USER_ACCESS_DENIED, null, USER_ACCESS_DENIED.getMessage())
            ));
        }
    }

    private void validatePassword(String inputPassword, String storedPassword) {
        if (!passwordEncoder.matches(inputPassword, storedPassword)) {
            throw new BaseException(List.of(
                new ErrorDetail(INVALID_PASSWORD, "password", INVALID_PASSWORD.getMessage())
            ));
        }
    }
}
