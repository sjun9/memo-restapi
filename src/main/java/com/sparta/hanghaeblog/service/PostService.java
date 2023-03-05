package com.sparta.hanghaeblog.service;

import com.sparta.hanghaeblog.dto.PostRequestDto;
import com.sparta.hanghaeblog.dto.PostResponseDto;
import com.sparta.hanghaeblog.entity.Comment;
import com.sparta.hanghaeblog.entity.Post;
import com.sparta.hanghaeblog.entity.PostLike;
import com.sparta.hanghaeblog.repository.CommentRepository;
import com.sparta.hanghaeblog.repository.PostLikeRepository;
import com.sparta.hanghaeblog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public List<PostResponseDto> getAllPost(){
        List<PostResponseDto> list = new ArrayList<>();
        for(Post post :postRepository.findAllByOrderByCreatedAtDesc()){
            List<Comment> commentList = commentRepository.findByPostIdOrderByCreatedAtDesc(post.getId());
            list.add(new PostResponseDto(post, commentList));
        }
        return list;
    }

    @Transactional
    public PostResponseDto createPost(PostRequestDto postRequestDto, String username) {
        String title = postRequestDto.getTitle();
        String content = postRequestDto.getContent();
        Post post = new Post(title, content, username);

        postRepository.save(post);
        List<Comment> commentList = new ArrayList<>();
        return new PostResponseDto(post, commentList);
    }

    @Transactional
    public PostResponseDto getPost(Long id){
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 글이 존재 하지 않습니다.")
        );
        List<Comment> commentList = commentRepository.findByPostIdOrderByCreatedAtDesc(post.getId());
        return new PostResponseDto(post, commentList);
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
        List<Comment> commentList = commentRepository.findByPostIdOrderByCreatedAtDesc(post.getId());
        return new PostResponseDto(post, commentList);
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
        List<Comment> commentList = commentRepository.findByPostIdOrderByCreatedAtDesc(post.getId());
        return new PostResponseDto(post, commentList);
    }

    @Transactional
    public void deleteMyPost(Long id, String username){
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 글이 존재 하지 않습니다.")
        );
        if(post.isEqualUsername(username)) {
            commentRepository.deleteByPostId(post.getId());
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
        commentRepository.deleteByPostId(post.getId());
        postRepository.delete(post);
    }

    @Transactional
    public String updateLikePost(Long id, String username){
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 글이 존재 하지 않습니다.")
        );

        Optional<PostLike> postLike = postLikeRepository.findByUsernameAndPostId(username, id);
        if(postLike.isPresent()) {
            postLikeRepository.deleteByUsernameAndPost(username, post);
            post.minusLikeCount();
            return "Like -1";
        }else {
            postLikeRepository.save(new PostLike(post,username));
            post.plusLikeCount();
            return "Like +1";
        }
    }

}
