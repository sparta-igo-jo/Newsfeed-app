package com.example.newsfeed.domain.follow.repository;

import com.example.newsfeed.domain.follow.entity.Follow;
import com.example.newsfeed.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    @Query("SELECT f FROM Follow f " +
        "WHERE f.follower = :follower AND f.following = :following " +
        "AND f.follower.deletedAt IS NULL AND f.following.deletedAt IS NULL")
    Optional<Follow> findByFollowerAndFollowing(@Param("follower") User follower, @Param("following") User following);

    // 팔로잉 한 유저들의 ID 조회
    @Query("SELECT f.following.id FROM Follow f" +
        " WHERE f.follower.id = :userId AND f.following.deletedAt IS NULL")
    List<Long> findFollowingIdsByUserId(@Param("userId") Long userId);

    // 팔로워들의 ID 조회
    @Query("SELECT f.follower.id FROM Follow f" +
        " WHERE f.following.id = :userId AND f.follower.deletedAt IS NULL")
    List<Long> findFollowerIdsByUserId(@Param("userId") Long userId);

    // 삭제되는 유저가 팔로우하고 있던 대상 유저의 FollowersCount를 감소
    // 반환 시 업데이트된 행의 수를 반환함 -> 반환 타입(int, long) 설정 시 검증이나 로깅 가능
    @Modifying
    @Query("update User u set u.followersCount = u.followersCount - 1 " +
        "where u.id in (select f.following.id from Follow f where f.follower.id = :userId)")
    void decreaseFollowersOfFollowedUsers(@Param("userId") Long userId);

    // 삭제되는 유저를 팔로잉하고 있던 대상 유저의 FollowingsCount를 감소
    @Modifying
    @Query("update User u set u.followingsCount = u.followingsCount - 1 " +
        "where u.id in (select f.follower.id from Follow f where f.following.id = :userId)")
    void decreaseFollowingsOfFollowers(@Param("userId") Long userId);

    @Query("SELECT f FROM Follow f WHERE f.follower = :follower AND f.following.deletedAt IS NULL")
    Page<Follow> findByFollower(@Param("follower") User follower, Pageable pageable);

    @Query("SELECT f FROM Follow f WHERE f.following = :following AND f.follower.deletedAt IS NULL")
    Page<Follow> findByFollowing(@Param("following") User following, Pageable pageable);
}
