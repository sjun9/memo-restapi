package com.sparta.hanghaeblog.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity(name = "users")
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String userName;
    @Column
    private String password;
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    List<Post> posts = new ArrayList<>();

    @Builder
    public User(String userName, String password){
        this.userName = userName;
        this.password = password;
    }
}
