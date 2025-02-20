package com.example.newsfeed.domain.user.entity;

import com.example.newsfeed.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@Table(name = "users")
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String name;

    private String profileImage;

    private String description;

    private Long followersCount = 0L;

    private Long followingsCount = 0L;

    private LocalDateTime deletedAt;

    @Builder
    public User(String email, String password, String name, String profileImage) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.profileImage = profileImage;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void updateProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public void updateDescription(String description) {
        this.description = description;
    }

    // 팔로우할 때 증가
    public void increaseFollowings() {
        this.followingsCount++;
    }

    public void increaseFollowers() {
        this.followersCount++;
    }

    // 언팔로우할 때 감소 (0 이하로 내려가지 않게 보호)
    public void decreaseFollowings() {
        if (this.followingsCount > 0) {
            this.followingsCount--;
        }
    }

    public void decreaseFollowers() {
        if (this.followersCount > 0) {
            this.followersCount--;
        }
    }
}
