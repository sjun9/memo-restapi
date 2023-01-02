package com.sparta.hanghaeblog.entity;

import com.sparta.hanghaeblog.repository.CommentRepository;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
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
    private String username;
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<PostLike> postLikes = new ArrayList<>();

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @OrderBy (value = "createdAt desc" )
    private List<Comment> comments = new ArrayList<>();

    @Builder
    public Post(String title, String content, String username){
        this.title = title;
        this.content = content;
        this.username = username;
    }

    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }

    public Integer getLikeCount(){
        return postLikes.size();
    }

    public boolean isEqualUsername(String username){
        return this.username.equals(username);
    }
}
