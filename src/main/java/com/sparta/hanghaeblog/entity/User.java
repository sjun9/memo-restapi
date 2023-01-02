package com.sparta.hanghaeblog.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity(name = "users")
@NoArgsConstructor
public class User extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String username;
    @Column
    private String password;
    @Column
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum userRole;

    @Builder
    public User(String username, String password, UserRoleEnum userRole){
        this.username = username;
        this.password = password;
        this.userRole = userRole;
    }
}
