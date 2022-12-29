package com.sparta.hanghaeblog.controller;

import com.sparta.hanghaeblog.dto.PostRequestDto;
import com.sparta.hanghaeblog.dto.PostResponseDto;
import com.sparta.hanghaeblog.entity.UserRoleEnum;
import com.sparta.hanghaeblog.jwt.JwtUtil;
import com.sparta.hanghaeblog.security.UserDetailsImpl;
import com.sparta.hanghaeblog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
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
    public ResponseEntity<PostResponseDto> createPost(@Validated @RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return new ResponseEntity<>(postService.createPost(postRequestDto, userDetails.getUsername()),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> getPost(@PathVariable Long id){
        return new ResponseEntity<>(postService.getPost(id),HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponseDto> updatePost(
            @PathVariable Long id, @RequestBody @Validated PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        if(userDetails.getAuthorities().contains(UserRoleEnum.ADMIN)){
            return new ResponseEntity<>(postService.updateAdminPost(id, postRequestDto),HttpStatus.OK);
        } else {
            String username = userDetails.getUsername();
            return new ResponseEntity<>(postService.updateMyPost(id, postRequestDto, username),HttpStatus.OK);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        if(userDetails.getAuthorities().contains(UserRoleEnum.ADMIN)){
            postService.deleteAdminPost(id);
        } else {
            String username = userDetails.getUsername();
            postService.deleteMyPost(id, username);
        }
        return new ResponseEntity<>("delete success",HttpStatus.OK);
    }
}
