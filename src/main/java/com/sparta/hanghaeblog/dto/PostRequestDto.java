package com.sparta.hanghaeblog.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class PostRequestDto {
    @NotBlank
    private String title;
    @NotBlank
    private String content;

    @Builder
    public PostRequestDto(String title, String content){
        this.title = title;
        this.content = content;
    }
}
