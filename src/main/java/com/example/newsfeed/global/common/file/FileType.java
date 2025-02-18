package com.example.newsfeed.global.common.file;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FileType {

    PROFILE("profile"),
    FEEDS("feeds");

    private final String type;
}
