package com.example.newsfeed.global.config;

import com.example.newsfeed.global.common.file.service.FileService;
import com.example.newsfeed.global.common.file.service.LocalFileService;
import com.example.newsfeed.user.application.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FileServiceConfig {

    @Bean
    public FileService fileService(UserService userService) {
        return new LocalFileService(userService);
    }
}
