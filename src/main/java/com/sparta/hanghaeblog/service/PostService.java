package com.sparta.hanghaeblog.service;

import com.sparta.hanghaeblog.dto.PostRequestDto;
import com.sparta.hanghaeblog.dto.PostResponseDto;
import com.sparta.hanghaeblog.entity.Post;
import com.sparta.hanghaeblog.entity.PostLike;
import com.sparta.hanghaeblog.entity.User;
import com.sparta.hanghaeblog.repository.PostLikeRepository;
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
    private final PostLikeRepository postLikeRepository;

    @Transactional
    public List<PostResponseDto> getAllPost(){
        List<PostResponseDto> list = new ArrayList<>();
        for(Post post :postRepository.findAllByOrderByCreatedAtDesc()){
            list.add(new PostResponseDto(post));
        }
        return list;
    }

    @Transactional
    public PostResponseDto createPost(PostRequestDto postRequestDto, String username) {
        String title = postRequestDto.getTitle();
        String content = postRequestDto.getContent();
        Post post = new Post(title, content, username);

        postRepository.save(post);
        return new PostResponseDto(post);
    }

    @Transactional
    public PostResponseDto getPost(Long id){
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 글이 존재 하지 않습니다.")
        );
        return new PostResponseDto(post);
    }

    @Transactional
    public PostResponseDto updateMyPost(Long id, PostRequestDto postRequestDto, String username){
        String title = postRequestDto.getTitle();
        String content = postRequestDto.getContent();
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 글이 존재 하지 않습니다.")
        );
        if(post.isEqualUsername(username)) {
            post.update(title, content);
            postRepository.save(post);
        } else {
            throw new IllegalArgumentException("자신의 글만 수정할 수 있습니다.");
        }
        return new PostResponseDto(post);
    }

    @Transactional
    public PostResponseDto updateAdminPost(Long id, PostRequestDto postRequestDto){
        String title = postRequestDto.getTitle();
        String content = postRequestDto.getContent();
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 글이 존재 하지 않습니다.")
        );
        post.update(title, content);
        postRepository.save(post);
        return new PostResponseDto(post);
    }

    @Transactional
    public void deleteMyPost(Long id, String username){
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 글이 존재 하지 않습니다.")
        );
        if(post.isEqualUsername(username)) {
            postRepository.delete(post);
        } else {
            throw new IllegalArgumentException("자신의 글만 삭제할 수 있습니다.");
        }
    }

    @Transactional
    public void deleteAdminPost(Long id){
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 글이 존재 하지 않습니다.")
        );
        postRepository.delete(post);
    }

    @Transactional
    public String updateLikePost(Long id, String username){
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 글이 존재 하지 않습니다.")
        );
        for(PostLike postLike: post.getPostLikes()){
            if(postLike.getUsername().equals(username)){
                postLikeRepository.delete(postLike);
                return "minus";
            }
        }
        postLikeRepository.save(new PostLike(post,username));
        return "plus";
    }
}
