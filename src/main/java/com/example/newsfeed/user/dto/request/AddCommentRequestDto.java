package com.example.newsfeed.user.dto.request;

import com.example.newsfeed.user.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddCommentRequestDto {

    private Long id;
    private String comments;
    private String createDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
    private String updatedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
    private String user;
    private int feeds;

    public Comment toEntity() {
        return Comment.builder()
    }
}
