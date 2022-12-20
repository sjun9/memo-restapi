package com.sparta.hanghaeblog.dto;

import com.sparta.hanghaeblog.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentListDto {
    @NotBlank
    private Long id;
    @NotBlank
    private String content;
    @NotBlank
    private String userName;
    @NotBlank
    private LocalDateTime createAt;

    public CommentListDto(Long id, String content, String userName, LocalDateTime createAt){
        this.id = id;
        this.content = content;
        this.userName = userName;
        this.createAt = createAt;
    }

    public CommentListDto(Comment comment){
        this.id = comment.getId();
        this.content = comment.getContent();
        this.userName = comment.getUser().getUserName();
        this.createAt = comment.getCreatedAt();
    }
}
