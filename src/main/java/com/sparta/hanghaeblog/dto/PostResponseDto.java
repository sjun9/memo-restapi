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
    private Integer likeCount;
    @NotBlank
    private LocalDateTime createAt;
    private List<CommentResponseDto> comments;

    public PostResponseDto(Post post){
        this.id = post.getId();
        this.title = post.getTitle();
        this.username = post.getUsername();
        this.content = post.getContent();
        this.likeCount = post.getLikeCount();
        this.createAt = post.getCreatedAt();
        List<CommentResponseDto> comments = new ArrayList<>();
        for (Comment comment : post.getComments()) {
            comments.add(new CommentResponseDto(comment));
        }
        this.comments = comments;
    }
}
