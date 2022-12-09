package com.sparta.hanghaeblog.dto;

import com.sparta.hanghaeblog.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PostResponseDto {
    private Long id;
    private String title;
    private String name;
    private String content;
    private LocalDateTime createAt;

    public PostResponseDto(Post post){
        this.id = post.getId();
        this.title = post.getTitle();
        this.name = post.getName();
        this.content = post.getContent();
        this.createAt = post.getCreatedAt();
    }
}
