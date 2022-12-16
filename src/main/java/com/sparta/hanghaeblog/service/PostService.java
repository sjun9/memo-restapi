package com.sparta.hanghaeblog.service;

import com.sparta.hanghaeblog.dto.PostRequestDto;
import com.sparta.hanghaeblog.dto.PostResponseDto;
import com.sparta.hanghaeblog.entity.Post;
import com.sparta.hanghaeblog.entity.User;
import com.sparta.hanghaeblog.jwt.JwtUtil;
import com.sparta.hanghaeblog.repository.PostRepository;
import com.sparta.hanghaeblog.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public ResponseEntity<List<PostResponseDto>> getAllPost(){
        List<PostResponseDto> list = new ArrayList<>();
        for(Post post :postRepository.findAllByOrderByCreatedAt()){
            list.add(new PostResponseDto(post));
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<PostResponseDto> createPost(PostRequestDto postRequestDto, HttpServletRequest request) {
        String title = postRequestDto.getTitle();
        String content = postRequestDto.getContent();

        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token.equals("error")) {
            throw new IllegalArgumentException("Token Resolve Error");
        }

        if (jwtUtil.validateToken(token)) {
            claims = jwtUtil.getUserInfoFromToken(token);
        } else {
            throw new IllegalArgumentException("Token Validate Error");
        }
        User user = userRepository.findByUserName(claims.getSubject()).orElseThrow(
                () -> new IllegalArgumentException("로그인을 확인해주세요.")
        );

        Post post = new Post(title, content, user);
        postRepository.saveAndFlush(post);
        return new ResponseEntity<>(new PostResponseDto(post),HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<PostResponseDto> getPost(Long id){
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 글이 존재 하지 않습니다.")
        );
        return new ResponseEntity<>(new PostResponseDto(post),HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<PostResponseDto> updatePost(Long id, PostRequestDto postRequestDto, HttpServletRequest request){
        String title = postRequestDto.getTitle();
        String content = postRequestDto.getContent();

        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token.equals("error")) {
            throw new IllegalArgumentException("Token Resolve Error");
        }

        if (jwtUtil.validateToken(token)) {
            claims = jwtUtil.getUserInfoFromToken(token);
        } else {
            throw new IllegalArgumentException("Token Validate Error");
        }
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 글이 존재 하지 않습니다.")
        );
        User user = userRepository.findByUserName(claims.getSubject()).orElseThrow(
                () -> new IllegalArgumentException("로그인을 확인해주세요.")
        );
        if(!post.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("자신의 글만 수정할 수 있습니다.");
        }
        post.update(title, content);
        return new ResponseEntity<>(new PostResponseDto(post),HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<String> deletePost(Long id, HttpServletRequest request){
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token.equals("error")) {
            throw new IllegalArgumentException("Token Resolve Error");
        }

        if(jwtUtil.validateToken(token)){
            claims = jwtUtil.getUserInfoFromToken(token);
        } else {
            throw new IllegalArgumentException("Token Validate Error");
        }
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 글이 존재 하지 않습니다.")
        );
        User user = userRepository.findByUserName(claims.getSubject()).orElseThrow(
                () -> new IllegalArgumentException("로그인을 확인해주세요")
        );
        if(!post.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("자신의 글만 삭제할 수 있습니다.");
        }
        postRepository.delete(post);
        return new ResponseEntity<>("delete success",HttpStatus.OK);
    }
}
