package com.sparta.hanghaeblog.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class CommentLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private Long commentId;
    @Column
    private String username;

    public CommentLike(Long commentId, String username){
        this.commentId = commentId;
        this.username = username;
    }
}
