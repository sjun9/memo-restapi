package com.sparta.hanghaeblog.entity;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Comment extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String content;
    @Column
    private String username;
    @Column
    private Integer likeCount;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Builder
    public Comment(String content, String username, Post post){
        this.likeCount = 0;
        this.content = content;
        this.username = username;
        this.post = post;
    }

    public void updateContent(String content){
        this.content = content;
    }

    public void plusLikeCount(){
        this.likeCount++;
    }

    public void minusLikeCount(){
        this.likeCount--;
    }
    public boolean isEqualUsername(String username){
        return this.username.equals(username);
    }
}
