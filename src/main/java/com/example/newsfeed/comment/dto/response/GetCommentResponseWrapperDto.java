package com.example.newsfeed.comment.dto.response;

public class GetCommentResponseWrapperDto<T> {

    private T data;

    private GetCommentResponseWrapperDto(T data) {
        this.data = data;
    }

    public static <T> GetCommentResponseWrapperDto<T> of(T data) {
        return new GetCommentResponseWrapperDto<>(data);
    }

    public static <T> GetCommentResponseWrapperDto<T> empty() {
        return new GetCommentResponseWrapperDto<>(null);
    }

    public T getData() {
        return data;
    }
}
