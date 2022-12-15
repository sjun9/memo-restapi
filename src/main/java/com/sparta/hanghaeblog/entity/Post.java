package com.sparta.hanghaeblog.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Post extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String title;
    @Column
    private String userName;
    @Column
    private String content;
    @Column
    private Long userId;

    @Builder
    public Post(String title, String userName, String content, Long userId){
        this.title = title;
        this.userName = userName;
        this.content = content;
        this.userId = userId;
    }

    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }

}
