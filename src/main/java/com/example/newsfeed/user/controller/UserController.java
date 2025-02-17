package com.example.newsfeed.user.controller;

import com.example.newsfeed.global.response.Response;
import com.example.newsfeed.user.application.service.UserService;
import com.example.newsfeed.user.dto.request.DeleteUserRequestDto;
import com.example.newsfeed.user.dto.request.SearchUserRequestDto;
import com.example.newsfeed.user.dto.request.UpdateUserPasswordRequestDto;
import com.example.newsfeed.user.dto.request.UpdateUserRequestDto;
import com.example.newsfeed.user.dto.response.GetAllUsersResponseDto;
import com.example.newsfeed.user.dto.response.GetUserResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    public Response<GetUserResponseDto> getUser(@PathVariable Long userId) {
        GetUserResponseDto getUserDto = userService.findUser(userId);
        return Response.of(getUserDto, "유저 단건 조회 성공");
    }

    @GetMapping
    public Response<Page<GetAllUsersResponseDto>> getUsers(
        @Valid @RequestBody SearchUserRequestDto dto,
        @SortDefault(sort = "name", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        Page<GetAllUsersResponseDto> getUsersDto = userService.findUsers(dto, pageable);
        return Response.of(getUsersDto, "유저 키워드 검색 성공");
    }

    @PatchMapping("/{userId}/profile")
    public Response<Long> updateUserProfile(
        @PathVariable Long userId,
        @Valid @RequestBody UpdateUserRequestDto dto,
        @SessionAttribute Long sessionUserId
    ) {
        Long updatedUserId = userService.updateUserProfile(userId, dto, sessionUserId);
        return Response.of(updatedUserId, "프로필 수정 성공");
    }

    @PatchMapping("/{userId}/password")
    public Response<Long> updateUserPassword(
        @PathVariable Long userId,
        @Valid @RequestBody UpdateUserPasswordRequestDto dto,
        @SessionAttribute Long sessionUserId
    ) {
        Long updatedUserId = userService.updateUserPassword(userId, dto, sessionUserId);
        return Response.of(updatedUserId, "패스워드 교체 성공");
    }

    @PostMapping("/{userId}/delete")
    public Response<Void> deleteUser(
        @PathVariable Long userId,
        @Valid @RequestBody DeleteUserRequestDto dto,
        @SessionAttribute Long sessionUserId
    ) {
        userService.deleteUser(userId, dto, sessionUserId);
        return Response.empty("회원 탈퇴 성공");
    }
}
