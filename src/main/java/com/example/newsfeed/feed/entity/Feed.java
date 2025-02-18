package com.example.newsfeed.feed.entity;

import com.example.newsfeed.global.common.entity.BaseTimeEntity;
import com.example.newsfeed.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "feeds")
public class Feed extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String title;

    private String contents;

    private String feedImage;

    private Long likeCount = 0L;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // TODO: 파일 저장 및 로드는 완성되면 추가해야 함
    @Builder
    public Feed(String title, String contents, User user, String feedImage) {
        this.title = title;
        this.contents = contents;
        this.user = user;
        this.feedImage = feedImage;
    }

    public void updateTitle(String title) { this.title = title; }
    public void updateContents(String contents) { this.contents = contents; }
    public void updateFeedImage(String feedImage) { this.feedImage = feedImage; }

    // 좋아요 개수 증가
    public void increaseLikeCount() {
        this.likeCount++;
    }

    // 좋아요 개수 감소
    public void decreaseLikeCount() {
        if (this.likeCount > 0) {
            this.likeCount--;
        }
    }
}
