package com.example.newsfeed.user.controller;

import com.example.newsfeed.global.response.Response;
import com.example.newsfeed.user.application.service.UserReadService;
import com.example.newsfeed.user.application.service.UserWriteService;
import com.example.newsfeed.user.dto.request.*;
import com.example.newsfeed.user.dto.response.GetAllUsersResponseDto;
import com.example.newsfeed.user.dto.response.GetUserResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.*;

import static com.example.newsfeed.global.common.constant.SessionConst.LOGIN_USER;
import static org.springframework.data.domain.Sort.Direction.ASC;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserWriteService userWriteService;
    private final UserReadService userReadService;

    @GetMapping("/{userId}")
    public Response<GetUserResponseDto> getUser(@PathVariable Long userId) {
        GetUserResponseDto getUserDto = userReadService.findUser(userId);
        return Response.of(getUserDto, "유저 단건 조회 성공");
    }

    @GetMapping
    public Response<Page<GetAllUsersResponseDto>> getUsersWithKeyword(
        @Valid @RequestBody SearchUserRequestDto dto,
        @SortDefault(sort = "name", direction = ASC) Pageable pageable
    ) {
        Page<GetAllUsersResponseDto> getUsersDto = userReadService.findUsersWithKeyword(dto, pageable);
        return Response.of(getUsersDto, "유저 키워드 검색 성공");
    }

    @GetMapping("/list")
    public Response<Page<GetAllUsersResponseDto>> getUsers(
            @RequestBody GetUsersRequestDto dto,
            @SortDefault(sort = "name", direction = ASC) Pageable pageable
    ){
        Page<GetAllUsersResponseDto> getUserDto = userReadService.findUsers(dto, pageable);
        return Response.of(getUserDto, "유저 목록 조회 성공");
    }

    @PatchMapping("/{userId}/profile")
    public Response<Long> updateUserProfile(
        @PathVariable Long userId,
        @Valid @RequestBody UpdateUserRequestDto dto,
        @SessionAttribute(LOGIN_USER) Long sessionUserId
    ) {
        Long updatedUserId = userWriteService.updateUserProfile(userId, dto, sessionUserId);
        return Response.of(updatedUserId, "프로필 수정 성공");
    }

    @PatchMapping("/{userId}/password")
    public Response<Long> updateUserPassword(
        @PathVariable Long userId,
        @Valid @RequestBody UpdateUserPasswordRequestDto dto,
        @SessionAttribute(LOGIN_USER) Long sessionUserId
    ) {
        Long updatedUserId = userWriteService.updateUserPassword(userId, dto, sessionUserId);
        return Response.of(updatedUserId, "패스워드 교체 성공");
    }

    @PostMapping("/{userId}/delete")
    public Response<Void> deleteUser(
        @PathVariable Long userId,
        @Valid @RequestBody DeleteUserRequestDto dto,
        @SessionAttribute(LOGIN_USER) Long sessionUserId
    ) {
        userWriteService.deleteUser(userId, dto, sessionUserId);
        return Response.empty("회원 탈퇴 성공");
    }
}
