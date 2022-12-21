package com.sparta.hanghaeblog.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
    private String userName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Builder
    public Comment(String content, String userName, Post post){
        this.content = content;
        this.userName = userName;
        this.post = post;
    }

    public void updateContent(String content){
        this.content = content;
    }

    public boolean isEqualUserName(String userName){
        return this.userName.equals(userName);
    }
}
