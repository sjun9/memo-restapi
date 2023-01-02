package com.sparta.hanghaeblog.dto;

import com.sparta.hanghaeblog.entity.Comment;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentResponseDto {
    @NotBlank
    private Long id;
    @NotBlank
    private String content;
    @NotBlank
    private LocalDateTime createAt;
    @NotBlank
    private String username;

    @Builder
    public CommentResponseDto(Long id, String content, LocalDateTime createAt, String username){
        this.id = id;
        this.content = content;
        this.createAt = createAt;
        this.username = username;
    }

    public CommentResponseDto(Comment comment){
        this.id = comment.getId();
        this.content = comment.getContent();
        this.createAt = comment.getCreatedAt();
        this.username = comment.getUsername();
    }
}
