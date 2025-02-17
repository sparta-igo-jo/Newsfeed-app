package com.example.newsfeed.user.dto.request;

import com.example.newsfeed.user.entity.Comment;
import com.example.newsfeed.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateCommentRequestDto {

    private String content;
    private Long userId;
    private Long feedsId;

    public Comment toEntity(User user, Feeds feeds) {
        return Comment.builder()
                .content(content)
                .user(user)
                .feeds(feeds)
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();
    }
}
