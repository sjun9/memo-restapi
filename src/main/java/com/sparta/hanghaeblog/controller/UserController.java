package com.sparta.hanghaeblog.controller;

import com.sparta.hanghaeblog.dto.LoginRequestDto;
import com.sparta.hanghaeblog.dto.LoginResponseDto;
import com.sparta.hanghaeblog.dto.SignupRequestDto;
import com.sparta.hanghaeblog.entity.UserRoleEnum;
import com.sparta.hanghaeblog.jwt.JwtUtil;
import com.sparta.hanghaeblog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Validated @RequestBody SignupRequestDto signupRequestDto){
        userService.signup(signupRequestDto, UserRoleEnum.USER);
        return new ResponseEntity<>("signup success", HttpStatus.OK);
    }

    @PostMapping("/admin/signup")
    public ResponseEntity<String> adminSignup(@Validated @RequestBody SignupRequestDto signupRequestDto){
        if (!signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
            throw new IllegalArgumentException("관리자 암호가 잘못되어 등록이 불가능합니다.");
        }
        userService.signup(signupRequestDto, UserRoleEnum.ADMIN);
        return new ResponseEntity<>("admin signup success", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Validated @RequestBody LoginRequestDto loginRequestDto){
        LoginResponseDto loginResponseDto = userService.login(loginRequestDto);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(JwtUtil.AUTHORIZATION_HEADER,jwtUtil.createToken(loginResponseDto.getUsername(),loginResponseDto.getUserRole()));
        return new ResponseEntity<>("login success",responseHeaders,HttpStatus.OK);
    }
}
