package com.example.newsfeed.comment.entity;

import com.example.newsfeed.feed.entity.Feed;
import com.example.newsfeed.global.common.entity.BaseTimeEntity;
import com.example.newsfeed.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "comments")
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content", nullable = false)
    private String content;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_id", nullable = false)
    private Feed feed;

    @Builder
    public Comment(String content, User user, Feed feed) {
        this.content = content;
        this.user = user;
        this.feed = feed;
    }

    public void updateContent(String content){
        this.content = content;
    }
}
