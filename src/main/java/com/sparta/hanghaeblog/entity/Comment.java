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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Builder
    public Comment(String content, User user, Post post){
        this.content = content;
        this.user = user;
        this.post = post;
    }

    public void updateContent(String content){
        this.content = content;
    }

    public boolean hasAuthority(String userName,UserRoleEnum userRole){
        return this.user.getUserName().equals(userName)||userRole.equals(UserRoleEnum.ADMIN);
    }
}
