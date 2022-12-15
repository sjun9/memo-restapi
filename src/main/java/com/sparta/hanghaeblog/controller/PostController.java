package com.sparta.hanghaeblog.controller;

import com.sparta.hanghaeblog.dto.PostRequestDto;
import com.sparta.hanghaeblog.dto.PostResponseDto;
import com.sparta.hanghaeblog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/posts")
@ResponseBody
public class PostController {
    private final PostService postService;

    @GetMapping("")
    public List<PostResponseDto> getAllPost(){
        return postService.getAllPost();
    }

    @PostMapping("")
    public PostResponseDto createPost(@Validated @RequestBody PostRequestDto postRequestDto, HttpServletRequest request){
        return postService.createPost(postRequestDto, request);
    }

    @GetMapping("/{id}")
    public PostResponseDto getPost(@PathVariable Long id){
        return postService.getPost(id);
    }

    @PutMapping("/{id}")
    public PostResponseDto updatePost(
            @PathVariable Long id, @RequestBody @Validated PostRequestDto postRequestDto, HttpServletRequest request){
        return postService.updatePost(id, postRequestDto, request);
    }

    @DeleteMapping("/{id}")
    public String deletePost(@PathVariable Long id,HttpServletRequest request){
        postService.deletePost(id, request);
        return "delete success";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String IllegalArgumentExceptionMessage(IllegalArgumentException e) {
        return e.getMessage();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String MethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return "빈칸을 입력해 주세요";
    }

}
