package com.sparta.hanghaeblog.dto;

import com.sparta.hanghaeblog.entity.Comment;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentListDto {
    @NotBlank
    private Long id;
    @NotBlank
    private String content;
    @NotBlank
    private String username;
    @NotBlank
    private LocalDateTime createAt;

    @Builder
    public CommentListDto(Long id, String content, String username, LocalDateTime createAt){
        this.id = id;
        this.content = content;
        this.username = username;
        this.createAt = createAt;
    }

    public CommentListDto(Comment comment){
        this.id = comment.getId();
        this.content = comment.getContent();
        this.username = comment.getUsername();
        this.createAt = comment.getCreatedAt();
    }
}
