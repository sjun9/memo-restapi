package com.sparta.hanghaeblog.entity;

import com.sparta.hanghaeblog.dto.SignupRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
    public User(SignupRequestDto signupRequestDto){
        this.userName = signupRequestDto.getUserName();
        this.password = signupRequestDto.getPassword();
    }
}
