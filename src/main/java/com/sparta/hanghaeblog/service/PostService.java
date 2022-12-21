package com.sparta.hanghaeblog.service;

import com.sparta.hanghaeblog.dto.CommentListDto;
import com.sparta.hanghaeblog.dto.PostRequestDto;
import com.sparta.hanghaeblog.dto.PostResponseDto;
import com.sparta.hanghaeblog.entity.Comment;
import com.sparta.hanghaeblog.entity.Post;
import com.sparta.hanghaeblog.entity.User;
import com.sparta.hanghaeblog.entity.UserRoleEnum;
import com.sparta.hanghaeblog.repository.PostRepository;
import com.sparta.hanghaeblog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public List<PostResponseDto> getAllPost(){
        List<PostResponseDto> list = new ArrayList<>();
        for(Post post :postRepository.findAllByOrderByCreatedAtDesc()){
            List<CommentListDto> commentList = new ArrayList<>();
            for(Comment comment: post.getComments()){
                commentList.add(new CommentListDto(comment));
            }
            list.add(new PostResponseDto(post,commentList));
        }
        return list;
    }

    @Transactional
    public PostResponseDto createPost(PostRequestDto postRequestDto, String userName) {
        String title = postRequestDto.getTitle();
        String content = postRequestDto.getContent();

        User user = userRepository.findByUserName(userName).orElseThrow(
                () -> new IllegalArgumentException("로그인을 확인해주세요.")
        );
        Post post = new Post(title, content, user.getId(), user.getUserName());
        postRepository.save(post);

        List<CommentListDto> commentList = new ArrayList<>();
        return new PostResponseDto(post,commentList);
    }

    @Transactional
    public PostResponseDto getPost(Long id){
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 글이 존재 하지 않습니다.")
        );

        List<CommentListDto> commentList = new ArrayList<>();
        for(Comment comment: post.getComments()){
            commentList.add(new CommentListDto(comment));
        }

        return new PostResponseDto(post,commentList);
    }

    @Transactional
    public PostResponseDto updatePost(Long id, PostRequestDto postRequestDto, String userName){
        String title = postRequestDto.getTitle();
        String content = postRequestDto.getContent();

        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 글이 존재 하지 않습니다.")
        );
        User user = userRepository.findByUserName(userName).orElseThrow(
                () -> new IllegalArgumentException("로그인을 확인해주세요.")
        );
        if(post.hasAuthority(user.getId(),user.getUserRole())) {
            post.update(title, content);
            postRepository.save(post);

            List<CommentListDto> commentList = new ArrayList<>();
            for (Comment comment : post.getComments()) {
                commentList.add(new CommentListDto(comment));
            }

            return new PostResponseDto(post, commentList);
        } else {
            throw new IllegalArgumentException("자신의 글만 수정할 수 있습니다.");
        }
    }

    @Transactional
    public void deletePost(Long id, String userName){
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 글이 존재 하지 않습니다.")
        );
        User user = userRepository.findByUserName(userName).orElseThrow(
                () -> new IllegalArgumentException("로그인을 확인해주세요")
        );
        if(post.hasAuthority(user.getId(),user.getUserRole())) {
            postRepository.delete(post);
        } else {
            throw new IllegalArgumentException("자신의 글만 삭제할 수 있습니다.");
        }
    }
}
