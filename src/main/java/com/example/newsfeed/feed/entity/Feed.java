package com.example.newsfeed.feed.entity;

import com.example.newsfeed.global.common.entity.BaseTimeEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "feeds")
public class Feed extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feed_id")
    private Long id;

    private String title;

    private String contents;

    private String feedImage;

    private Long likeCount = 0L;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Feed() {}

    public Feed(String title, String contents, String feedImage, User user) {
        this.title = title;
        this.contents = contents;
        this.feedImage = feedImage;
        this.user = user;
    }
}
