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
public class User extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String userName;
    @Column
    private String password;
    @Column
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum userRole;

    @Builder
    public User(String userName, String password, UserRoleEnum userRole){
        this.userName = userName;
        this.password = password;
        this.userRole = userRole;
    }

    public boolean checkPassword(String password){
        return this.password.equals(password);
    }
}
