package com.example.newsfeed.like.repository;

import com.example.newsfeed.like.entity.Follow;
import com.example.newsfeed.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
   Optional<Follow> findByFollowerAndFollowing(User follower, User following);

   // 팔로잉 한 유저들의 ID 조회
   @Query("SELECT f.following.id FROM Follow f" +
           " WHERE f.follower.id = :userId AND f.following.deletedAt IS NULL")
   List<Long> findFollowingIdsByUserId(@Param("userId") Long userId);
   // 팔로워들의 ID 조회
   @Query("SELECT f.follower.id FROM Follow f" +
           " WHERE f.following.id = :userId AND f.follower.deletedAt IS NULL")
   List<Long> findFollowerIdsByUserId(@Param("userId") Long userId);
}
