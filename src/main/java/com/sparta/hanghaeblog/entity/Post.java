package com.sparta.hanghaeblog.entity;

import com.sparta.hanghaeblog.dto.PostRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Post extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String title;
    @Column
    private String userName;
    @Column
    private String content;

    @Builder
    public Post(String title, String userName, String content){
        this.title = title;
        this.userName = userName;
        this.content = content;
    }

    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }

}
