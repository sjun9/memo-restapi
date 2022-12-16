package com.sparta.hanghaeblog.service;

import com.sparta.hanghaeblog.dto.LoginRequestDto;
import com.sparta.hanghaeblog.dto.SignupRequestDto;
import com.sparta.hanghaeblog.entity.User;
import com.sparta.hanghaeblog.jwt.JwtUtil;
import com.sparta.hanghaeblog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> signup(SignupRequestDto signupRequestDto){
        String userName = signupRequestDto.getUserName();
        String password = signupRequestDto.getPassword();

        Optional<User> found = userRepository.findByUserName(userName);
        if(found.isPresent()){
            throw new IllegalArgumentException("중복된 사용자가 존재합니다");
        }
        User user = new User(userName, password);
        userRepository.saveAndFlush(user);
        return new ResponseEntity<>("signup success", HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<String> login(LoginRequestDto loginRequestDto, HttpServletResponse response){
        User user = userRepository.findByUserName(loginRequestDto.getUserName()).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다")
        );

        if(!user.getPassword().equals(loginRequestDto.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다");
        }
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER,jwtUtil.createToken(user.getUserName()));
        return new ResponseEntity<>("login success",HttpStatus.OK);
    }
}
