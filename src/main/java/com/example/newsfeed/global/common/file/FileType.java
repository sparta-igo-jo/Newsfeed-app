package com.example.newsfeed.global.common.file;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FileType {

    PROFILE("profile"),
    FEEDS("feeds"),
    DEFAULT("default");

    private final String type;
}
