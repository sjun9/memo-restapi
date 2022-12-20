package com.sparta.hanghaeblog.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class CommentRequestDto {
    @NotBlank
    private String content;

    public CommentRequestDto(String content){
        this.content = content;
    }
}
