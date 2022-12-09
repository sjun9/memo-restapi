package com.sparta.hanghaeblog.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostRequestDto {
    private String title;
    private String name;
    private String password;
    private String content;
}
