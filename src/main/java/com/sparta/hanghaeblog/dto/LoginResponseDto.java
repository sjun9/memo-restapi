package com.sparta.hanghaeblog.dto;

import com.sparta.hanghaeblog.entity.UserRoleEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class LoginResponseDto {
    @NotBlank
    private String userName;
    @NotBlank
    private UserRoleEnum userRole;

    @Builder
    public LoginResponseDto(String userName, UserRoleEnum userRole){
        this.userName = userName;
        this.userRole = userRole;
    }
}
