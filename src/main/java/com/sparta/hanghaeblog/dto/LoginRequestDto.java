package com.sparta.hanghaeblog.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class LoginRequestDto {
    @Pattern(regexp = "[a-z0-9]{4,10}")
    @NotNull
    private String userName;
    @Pattern(regexp = "[a-zA-Z0-9]{8,15}")
    @NotNull
    private String password;

    @Builder
    public LoginRequestDto(String userName, String password){
        this.userName = userName;
        this.password = password;
    }
}
