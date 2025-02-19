package com.example.newsfeed.follow.entity;

import com.example.newsfeed.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@Table(
    name = "follows",
    uniqueConstraints = @UniqueConstraint(columnNames = {"follower_id", "following_id"}),
    indexes = {@Index(name = "idx_follower_following", columnList = "follower_id, following_id")})
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = PROTECTED)
public class Follow {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "follower_id", referencedColumnName = "id", nullable = false)
    private User follower;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "following_id", referencedColumnName = "id", nullable = false)
    private User following;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Builder
    public Follow(User follower, User following) {
        this.follower = follower;
        this.following = following;
    }
}
