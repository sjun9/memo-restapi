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
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;

    @GetMapping("/")
    @ResponseBody
    public List<PostResponseDto> getAllPost(){
        return postService.getAllPost();
    }

    @PostMapping("/")
    @ResponseBody
    public PostResponseDto createPost(@Validated @RequestBody PostRequestDto postRequestDto, HttpServletRequest request){
        return postService.createPost(postRequestDto, request);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public PostResponseDto getPost(@PathVariable Long id){
        return postService.getPost(id);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public PostResponseDto updatePost(
            @PathVariable Long id, @RequestBody @Validated PostRequestDto postRequestDto, HttpServletRequest request){
        return postService.updatePost(id, postRequestDto, request);
    }

    @DeleteMapping("/{id}")
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
