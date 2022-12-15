package com.sparta.hanghaeblog.controller;

import com.sparta.hanghaeblog.dto.LoginRequestDto;
import com.sparta.hanghaeblog.dto.SignupRequestDto;
import com.sparta.hanghaeblog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/user")
@ResponseBody
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public String signup(@Validated @RequestBody SignupRequestDto signupRequestDto){
        userService.signup(signupRequestDto);
        return "signup success";
    }

    @PostMapping("/login")
    public String login(@Validated @RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response){
        userService.login(loginRequestDto, response);
        return "login success";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String IllegalArgumentExceptionMessage(IllegalArgumentException e) {
        return e.toString().substring(e.toString().indexOf(":")+2);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String MethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return "이름은 4~10글자 알파벳(a~z), 숫자(0~9)로 구성되어야 합니다\n" +
                "비밀번호는 8~15글자 알파벳(A~Z, a~z), 숫자(0~9)로 구성되어야 합니다";
    }
}
