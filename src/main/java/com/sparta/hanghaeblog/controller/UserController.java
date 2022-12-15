package com.sparta.hanghaeblog.controller;

import com.sparta.hanghaeblog.dto.LoginRequestDto;
import com.sparta.hanghaeblog.dto.SignupRequestDto;
import com.sparta.hanghaeblog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    @ResponseBody
    public String signup(@Validated @RequestBody SignupRequestDto signupRequestDto){
        userService.signup(signupRequestDto);
        return "signup success";
    }

    @PostMapping("/login")
    @ResponseBody
    public String login(@Validated @RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response){
        userService.login(loginRequestDto, response);
        return "login success";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public String IllegalArgumentExceptionMessage(IllegalArgumentException e) {
        return e.toString();
    }

}
