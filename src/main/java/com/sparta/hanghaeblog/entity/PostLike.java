package com.sparta.hanghaeblog.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class PostLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long postId;
    private String username;

    public PostLike(Long postId, String username){
        this.postId = postId;
        this.username = username;
    }

}
