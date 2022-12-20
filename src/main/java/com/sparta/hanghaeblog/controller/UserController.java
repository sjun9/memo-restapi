package com.sparta.hanghaeblog.controller;

import com.sparta.hanghaeblog.dto.LoginRequestDto;
import com.sparta.hanghaeblog.dto.LoginResponseDto;
import com.sparta.hanghaeblog.dto.SignupRequestDto;
import com.sparta.hanghaeblog.jwt.JwtUtil;
import com.sparta.hanghaeblog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Validated @RequestBody SignupRequestDto signupRequestDto){
        userService.signup(signupRequestDto);
        return new ResponseEntity<>("signup success", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Validated @RequestBody LoginRequestDto loginRequestDto){
        LoginResponseDto loginResponseDto = userService.login(loginRequestDto);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(JwtUtil.AUTHORIZATION_HEADER,jwtUtil.createToken(loginResponseDto.getUserName(),loginResponseDto.getUserRole()));
        return new ResponseEntity<>("login success",responseHeaders,HttpStatus.OK);
    }
}
