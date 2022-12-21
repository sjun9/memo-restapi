package com.sparta.hanghaeblog.entity;

import com.sparta.hanghaeblog.repository.CommentRepository;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    private String content;
    @Column
    private String userName;
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @OrderBy (value = "createdAt desc" )
    private List<Comment> comments = new ArrayList<>();

    @Builder
    public Post(String title, String content, String userName){
        this.title = title;
        this.content = content;
        this.userName = userName;
    }

    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }

    public boolean isEqualUserName(String userName){
        return this.userName.equals(userName);
    }
}
