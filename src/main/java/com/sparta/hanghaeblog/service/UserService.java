package com.sparta.hanghaeblog.service;

import com.sparta.hanghaeblog.dto.LoginRequestDto;
import com.sparta.hanghaeblog.dto.SignupRequestDto;
import com.sparta.hanghaeblog.entity.User;
import com.sparta.hanghaeblog.jwt.JwtUtil;
import com.sparta.hanghaeblog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public void signup(SignupRequestDto signupRequestDto){
        Optional<User> found = userRepository.findByUserName(signupRequestDto.getUserName());
        if(found.isPresent()){
            throw new IllegalArgumentException("중복된 사용자가 존재합니다");
        }

        User user = new User(signupRequestDto);
    }

    @Transactional(readOnly = true)
    public void login(LoginRequestDto loginRequestDto, HttpServletResponse response){
        User user = userRepository.findByUserName(loginRequestDto.getUserName()).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다")
        );

        if(!user.getPassword().equals(loginRequestDto.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다");
        }
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER,jwtUtil.createToken(user.getUserName()));
    }

}
