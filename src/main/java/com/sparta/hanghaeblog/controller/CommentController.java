package com.sparta.hanghaeblog.controller;

import com.sparta.hanghaeblog.dto.CommentRequestDto;
import com.sparta.hanghaeblog.dto.CommentResponseDto;
import com.sparta.hanghaeblog.entity.UserRoleEnum;
import com.sparta.hanghaeblog.jwt.JwtUtil;
import com.sparta.hanghaeblog.service.CommentService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentController {
    private final CommentService commentService;
    private final JwtUtil jwtUtil;
    @PostMapping("/{id}")
    public ResponseEntity<CommentResponseDto> addComment(@PathVariable Long id,
            @RequestBody CommentRequestDto commentRequestDto, HttpServletRequest request){
        String userName = jwtUtil.getUserNameCheckedToken(request);
        return new ResponseEntity<>(commentService.addComment(id, commentRequestDto,userName), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long id,
            @RequestBody CommentRequestDto commentRequestDto, HttpServletRequest request){
        String userName = jwtUtil.getUserNameCheckedToken(request);
        UserRoleEnum userRole = jwtUtil.getUserRoleCheckedToken(request);
        return new ResponseEntity<>(commentService.updateComment(id,commentRequestDto,userName,userRole), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable Long id, HttpServletRequest request){
        String userName = jwtUtil.getUserNameCheckedToken(request);
        UserRoleEnum userRole = jwtUtil.getUserRoleCheckedToken(request);
        commentService.deleteComment(id,userName,userRole);
        return new ResponseEntity<>("success delete", HttpStatus.OK);
    }
}
