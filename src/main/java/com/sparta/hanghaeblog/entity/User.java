package com.sparta.hanghaeblog.entity;

import com.sparta.hanghaeblog.dto.UserRequestDto;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String userName;
    @Column
    private String password;

    @Builder
    public User(UserRequestDto userRequestDto){
        this.userName = userRequestDto.getUserName();
        this.password = userRequestDto.getPassword();
    }
}
