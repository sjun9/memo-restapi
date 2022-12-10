package com.sparta.hanghaeblog.controller;

import com.sparta.hanghaeblog.dto.PostRequestDto;
import com.sparta.hanghaeblog.dto.PostResponseDto;
import com.sparta.hanghaeblog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public PostResponseDto createPost(@RequestBody PostRequestDto postRequestDto){
        return postService.createPost(postRequestDto);
    }

    @GetMapping("/api/posts/{id}")
    @ResponseBody
    public PostResponseDto getPost(@PathVariable Long id){
        return postService.getPost(id);
    }

    @PutMapping("/api/posts/{id}")
    @ResponseBody
    public PostResponseDto updatePost(@PathVariable Long id,@RequestBody PostRequestDto postRequestDto){
        return postService.updatePost(id, postRequestDto);
    }

    @DeleteMapping("/api/posts/{id}")
    @ResponseBody
    public Boolean deletePost(@PathVariable Long id,@RequestParam String password){
        postService.deletePost(id,password);
        return true;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public String IllegalArgumentExceptionMessage(IllegalArgumentException e) {
        return e.toString();
    }

}
