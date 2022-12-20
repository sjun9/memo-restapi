package com.sparta.hanghaeblog.controller;

import com.sparta.hanghaeblog.dto.PostRequestDto;
import com.sparta.hanghaeblog.dto.PostResponseDto;
import com.sparta.hanghaeblog.jwt.JwtUtil;
import com.sparta.hanghaeblog.service.PostService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;
    private final JwtUtil jwtUtil;

    @GetMapping("")
    public ResponseEntity<List<PostResponseDto>> getAllPost(){
        return new ResponseEntity<>(postService.getAllPost(), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<PostResponseDto> createPost(@Validated @RequestBody PostRequestDto postRequestDto, HttpServletRequest request){
        Claims claims = jwtUtil.getUserInfoCheckedToken(request);
        String userName = claims.getSubject();
        return new ResponseEntity<>(postService.createPost(postRequestDto, userName),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> getPost(@PathVariable Long id){
        return new ResponseEntity<>(postService.getPost(id),HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponseDto> updatePost(
            @PathVariable Long id, @RequestBody @Validated PostRequestDto postRequestDto, HttpServletRequest request){
        Claims claims = jwtUtil.getUserInfoCheckedToken(request);
        String userName = claims.getSubject();
        return new ResponseEntity<>(postService.updatePost(id, postRequestDto, userName),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id,HttpServletRequest request){
        Claims claims = jwtUtil.getUserInfoCheckedToken(request);
        String userName = claims.getSubject();
        postService.deletePost(id, userName);
        return new ResponseEntity<>("delete success",HttpStatus.OK);
    }
}
