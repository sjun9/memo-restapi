package com.sparta.hanghaeblog.service;

import com.sparta.hanghaeblog.dto.PostRequestDto;
import com.sparta.hanghaeblog.dto.PostResponseDto;
import com.sparta.hanghaeblog.entity.Post;
import com.sparta.hanghaeblog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    @Transactional
    public List<PostResponseDto> getAllPost(){
        List<PostResponseDto> list = new ArrayList<>();
        for(Post post :postRepository.findAllByOrderByCreatedAt()){
            list.add(new PostResponseDto(post));
        }
        return list;
    }

    @Transactional
    public PostResponseDto createPost(PostRequestDto postRequestDto){
        Post post = new Post(postRequestDto);
        postRepository.save(post);
        return new PostResponseDto(post);
    }

    @Transactional
    public PostResponseDto getSelectPost(Long id){
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재 하지 않습니다")
        );
        return new PostResponseDto(post);
    }

    @Transactional
    public PostResponseDto updatePost(Long id, PostRequestDto postRequestDto){
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재 하지 않습니다")
        );
        if(!post.getPassword().equals(postRequestDto.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다");
        }
        post.update(postRequestDto);
        return new PostResponseDto(post);
    }

    @Transactional
    public Boolean deletePost(Long id, String password){
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재 하지 않습니다")
        );
        if(!post.getPassword().equals(password)) {
            return false;
        }

        postRepository.delete(post);
        return true;
    }

}
