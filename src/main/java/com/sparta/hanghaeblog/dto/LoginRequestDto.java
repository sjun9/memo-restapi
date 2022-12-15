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
    @Pattern(regexp = "[a-z0-9]{4,10}", message = "이름은 숫자나 알파벳 소문자 4~10자리 입력해주세요")
    @NotNull(message = "이름을 입력해주세요")
    private String userName;
    @Pattern(regexp = "[a-zA-Z0-9]{8,15}", message = "비밀번호를 숫자나 알파벳 8~15자리 입력해주세요")
    @NotNull(message = "비밀번호를 입력해주세요")
    private String password;

    @Builder
    public LoginRequestDto(String userName, String password){
        this.userName = userName;
        this.password = password;
    }
}
