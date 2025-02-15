package com.example.newsfeed.user.application.service;

import com.example.newsfeed.global.common.exception.ErrorDetail;
import com.example.newsfeed.user.application.converter.UserConverter;
import com.example.newsfeed.user.dto.request.DeleteUserRequestDto;
import com.example.newsfeed.user.dto.request.UpdateUserPasswordRequestDto;
import com.example.newsfeed.user.dto.request.UpdateUserRequestDto;
import com.example.newsfeed.user.dto.response.GetAllUsersResponseDto;
import com.example.newsfeed.user.dto.response.GetUserResponseDto;
import com.example.newsfeed.user.entity.User;
import com.example.newsfeed.user.exception.InvalidPasswordException;
import com.example.newsfeed.user.exception.PasswordSameAsOldException;
import com.example.newsfeed.user.exception.UserNotFoundException;
import com.example.newsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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

    @Transactional(readOnly = true)
    public GetUserResponseDto findUser(Long userId) {
        User findUser = findUserById(userId);
        return UserConverter.toResponse(findUser);
    }

    @Transactional(readOnly = true)
    public List<GetAllUsersResponseDto> findUsers() {
        List<User> users = userRepository.findAllUsers();
        return UserConverter.toResponse(users);
    }

    @Transactional
    public Long updateUserProfile(Long userId, UpdateUserRequestDto dto) {
        //TODO: 회원 인증 로직 구현 이후 로그인 유저와 저장된 유저 간의 검증 필요
        User findUser = findUserById(userId);

        validatePassword(dto.getPassword(), findUser.getPassword());

        if (dto.getName() != null) {
            findUser.updateName(dto.getName());
        }

        if (dto.getDescription() != null) {
            findUser.updateDescription(dto.getDescription());
        }

        //TODO: 파일 저장 및 로드는 완성되면 추가해야 함

        return findUser.getId();
    }

    @Transactional
    public Long updateUserPassword(Long userId, UpdateUserPasswordRequestDto dto) {
        //TODO: 회원 인증 로직 구현 이후 로그인 유저와 저장된 유저 간의 검증 필요
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

    @Transactional
    public void deleteUser(Long userId, DeleteUserRequestDto dto) {
        //TODO: 회원 인증 로직 구현 이후 로그인 유저와 저장된 유저 간의 검증 필요
        User findUser = findUserById(userId);
        validatePassword(dto.getPassword(), findUser.getPassword());
        userRepository.deleteUserById(userId);
    }

    public User findUserById(Long userId) {
        //TODO: public으로 열지 않고 다른 도메인의 서비스에서도 호출하도록 로직을 바꿀 수 있는지 확인
        return userRepository.findUserById(userId)
            .orElseThrow(() -> new UserNotFoundException(List.of(
                new ErrorDetail(USER_NOT_FOUND, null, USER_NOT_FOUND.getMessage())
            )));
    }

    private void validatePassword(String inputPassword, String storedPassword) {
        if (!passwordEncoder.matches(inputPassword, storedPassword)) {
            throw new InvalidPasswordException(List.of(
                new ErrorDetail(INVALID_PASSWORD, "password", INVALID_PASSWORD.getMessage())
            ));
        }
    }
}
