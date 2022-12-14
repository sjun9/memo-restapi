package com.sparta.hanghaeblog.entity;

import com.sparta.hanghaeblog.dto.PostRequestDto;
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
    private String name;

    @Column
    private String password;

    @Column
    private String content;

    public Post(PostRequestDto postRequestDto){
        this.title = postRequestDto.getTitle();
        this.name = postRequestDto.getName();
        this.password = postRequestDto.getPassword();
        this.content = postRequestDto.getContent();
    }

    public void update(PostRequestDto postRequestDto){
        this.title = postRequestDto.getTitle();
        this.name = postRequestDto.getName();
        this.content = postRequestDto.getContent();
    }

}
