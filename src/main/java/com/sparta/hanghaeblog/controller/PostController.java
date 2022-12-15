package com.sparta.hanghaeblog.controller;

import com.sparta.hanghaeblog.dto.PostRequestDto;
import com.sparta.hanghaeblog.dto.PostResponseDto;
import com.sparta.hanghaeblog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping("/api/posts")
    @ResponseBody
    public List<PostResponseDto> getAllPost(){
        return postService.getAllPost();
    }

    @PostMapping("/api/posts")
    @ResponseBody
    public PostResponseDto createPost(@Validated @RequestBody PostRequestDto postRequestDto, HttpServletRequest request){
        return postService.createPost(postRequestDto, request);
    }

    @GetMapping("/api/posts/{id}")
    @ResponseBody
    public PostResponseDto getPost(@PathVariable Long id){
        return postService.getPost(id);
    }

    @PutMapping("/api/posts/{id}")
    @ResponseBody
    public PostResponseDto updatePost(
            @PathVariable Long id, @RequestBody @Validated PostRequestDto postRequestDto, HttpServletRequest request){
        return postService.updatePost(id, postRequestDto, request);
    }

    @DeleteMapping("/api/posts/{id}")
    @ResponseBody
    public String deletePost(@PathVariable Long id,HttpServletRequest request){
        postService.deletePost(id, request);
        return "delete success";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public String IllegalArgumentExceptionMessage(IllegalArgumentException e) {
        return e.toString();
    }

}
