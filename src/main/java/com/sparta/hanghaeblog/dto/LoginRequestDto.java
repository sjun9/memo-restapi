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
    @Pattern(regexp = "(?=.*[a-z])(?=.*[0-9])[a-z0-9]{4,10}", message = "이름은 소문자+숫자 4~10자리 입력해주세요")
    @NotNull(message = "이름을 입력해주세요")
    private String userName;
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[~!@#$%^&*()_+])[a-zA-Z0-9~!@#$%^&*()_+]{8,15}", message = "비밀번호는 알파벳+숫자+특수문자 8~15자리 입력해주세요")
    @NotNull(message = "비밀번호를 입력해주세요")
    private String password;

    @Builder
    public LoginRequestDto(String userName, String password){
        this.userName = userName;
        this.password = password;
    }
}
