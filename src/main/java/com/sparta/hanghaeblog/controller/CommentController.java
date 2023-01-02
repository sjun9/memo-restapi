package com.sparta.hanghaeblog.controller;

import com.sparta.hanghaeblog.dto.CommentRequestDto;
import com.sparta.hanghaeblog.dto.CommentResponseDto;
import com.sparta.hanghaeblog.entity.UserRoleEnum;
import com.sparta.hanghaeblog.jwt.JwtUtil;
import com.sparta.hanghaeblog.security.UserDetailsImpl;
import com.sparta.hanghaeblog.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;



@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/{postId}")
    public ResponseEntity<CommentResponseDto> addComment(@PathVariable Long postId,
                                                         @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return new ResponseEntity<>(commentService.addComment(postId, commentRequestDto,userDetails.getUsername()), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentResponseDto> updateMtComment(@PathVariable Long id,
            @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return new ResponseEntity<>(commentService.updateMyComment(id, commentRequestDto, userDetails.getUsername()), HttpStatus.OK);
    }

    @PutMapping("/admin/{id}")
    public ResponseEntity<CommentResponseDto> updateAdminComment(@PathVariable Long id,
                                                            @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return new ResponseEntity<>(commentService.updateAdminComment(id, commentRequestDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMyComment(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        commentService.deleteMyComment(id,userDetails.getUsername());
        return new ResponseEntity<>("success delete", HttpStatus.OK);
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        commentService.deleteAdminComment(id);
        return new ResponseEntity<>("success delete", HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updateLikeComment(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return new ResponseEntity<>(commentService.updateLikeComment(id,userDetails.getUsername()),HttpStatus.OK);
    }
}
