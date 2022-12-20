package com.sparta.hanghaeblog.dto;

import com.sparta.hanghaeblog.entity.Comment;
import com.sparta.hanghaeblog.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;


@Getter
@NoArgsConstructor
public class PostResponseDto {
    @NotBlank
    private Long id;
    @NotBlank
    private String title;
    @NotBlank
    private String userName;
    @NotBlank
    private String content;
    @NotBlank
    private LocalDateTime createAt;
    private List<CommentListDto> comments;

    public PostResponseDto(Post post, List<CommentListDto> comments){
        this.id = post.getId();
        this.title = post.getTitle();
        this.userName = post.getUser().getUserName();
        this.content = post.getContent();
        this.createAt = post.getCreatedAt();
        this.comments = comments;
    }
}
