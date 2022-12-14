package com.sparta.hanghaeblog.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class SignupRequestDto {
    @Size(min = 4,max = 10)
    @Pattern(regexp = "[a-z0-9]")
    private String userName;
    @Size(min = 8,max = 15)
    @Pattern(regexp = "[a-zA-Z0-9]")
    private String password;

    @Builder
    public SignupRequestDto(String userName, String password){
        this.userName = userName;
        this.password = password;
    }

}
