package com.sparta.hanghaeblog.dto;

import com.sparta.hanghaeblog.entity.UserRoleEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class LoginResponseDto {
    @NotBlank
    private String username;
    @NotBlank
    private UserRoleEnum userRole;

    @Builder
    public LoginResponseDto(String username, UserRoleEnum userRole){
        this.username = username;
        this.userRole = userRole;
    }
}
