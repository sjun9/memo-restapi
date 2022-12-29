package com.sparta.hanghaeblog.dto;

import com.sparta.hanghaeblog.entity.Comment;
import com.sparta.hanghaeblog.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@NoArgsConstructor
public class PostResponseDto {
    @NotBlank
    private Long id;
    @NotBlank
    private String title;
    @NotBlank
    private String username;
    @NotBlank
    private String content;
    @NotBlank
    private LocalDateTime createAt;
    private List<CommentListDto> comments;

    public PostResponseDto(Post post){
        this.id = post.getId();
        this.title = post.getTitle();
        this.username = post.getUsername();
        this.content = post.getContent();
        this.createAt = post.getCreatedAt();
        List<CommentListDto> comments = new ArrayList<>();
        for (Comment comment : post.getComments()) {
            comments.add(new CommentListDto(comment));
        }
        this.comments = comments;
    }
}
