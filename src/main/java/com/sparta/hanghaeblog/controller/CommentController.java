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
    @PostMapping("/{postId}")
    public ResponseEntity<CommentResponseDto> addComment(@PathVariable Long postId,
            @RequestBody CommentRequestDto commentRequestDto, HttpServletRequest request){
        String userName = jwtUtil.getUserNameCheckedToken(request);
        return new ResponseEntity<>(commentService.addComment(postId, commentRequestDto,userName), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long id,
            @RequestBody CommentRequestDto commentRequestDto, HttpServletRequest request){
        UserRoleEnum userRole = jwtUtil.getUserRoleCheckedToken(request);
        if(userRole.equals(UserRoleEnum.ADMIN)){
            return new ResponseEntity<>(commentService.updateAdminComment(id, commentRequestDto), HttpStatus.OK);
        }else {
            String userName = jwtUtil.getUserNameCheckedToken(request);
            return new ResponseEntity<>(commentService.updateMyComment(id, commentRequestDto, userName), HttpStatus.OK);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable Long id, HttpServletRequest request){
        UserRoleEnum userRole = jwtUtil.getUserRoleCheckedToken(request);
        if(userRole.equals(UserRoleEnum.ADMIN)){
            commentService.deleteAdminComment(id);
        } else {
            String userName = jwtUtil.getUserNameCheckedToken(request);
            commentService.deleteMyComment(id,userName);
        }
        return new ResponseEntity<>("success delete", HttpStatus.OK);
    }
}
