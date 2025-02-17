package com.example.newsfeed.like.controller;

import com.example.newsfeed.global.response.Response;
import com.example.newsfeed.like.application.service.FollowService;
import com.example.newsfeed.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users/{userId}/follows")
@RequiredArgsConstructor

public class FollowController {
   private final FollowService followService;

   @PostMapping("/{targetUserId}")
   public Response<String> follow(@PathVariable Long userId,
                                  @PathVariable Long targetUserId
   ){
      boolean isFollowing = followService.toggleFollow(userId, targetUserId);
      return Response.of(isFollowing ? "팔로우" : "언팔로우");
   }
}