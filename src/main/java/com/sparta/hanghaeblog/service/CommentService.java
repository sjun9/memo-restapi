package com.sparta.hanghaeblog.service;

import com.sparta.hanghaeblog.dto.CommentRequestDto;
import com.sparta.hanghaeblog.dto.CommentResponseDto;
import com.sparta.hanghaeblog.entity.Comment;
import com.sparta.hanghaeblog.entity.Post;
import com.sparta.hanghaeblog.entity.User;
import com.sparta.hanghaeblog.entity.UserRoleEnum;
import com.sparta.hanghaeblog.repository.CommentRepository;
import com.sparta.hanghaeblog.repository.PostRepository;
import com.sparta.hanghaeblog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public CommentResponseDto addComment(Long id, CommentRequestDto commentRequestDto, String userName){
        Long postId = id;
        String content = commentRequestDto.getContent();

        User user = userRepository.findByUserName(userName).orElseThrow(
                () -> new IllegalArgumentException("로그인을 확인해 주세요.")
        );
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("해당 글이 존재하지 않습니다.")
        );
        Comment comment = new Comment(content, user, post);
        commentRepository.saveAndFlush(comment);
        return new CommentResponseDto(comment);
    }

    @Transactional
    public CommentResponseDto updateComment(Long id, CommentRequestDto commentRequestDto,
                                            String userName, String userRole){
        Long commentId = id;
        String content = commentRequestDto.getContent();
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다.")
        );
        if(comment.isEqualUserName(userName)||userRole.equals(UserRoleEnum.ADMIN.toString())){
            comment.updateContent(content);
        } else {
            throw new IllegalArgumentException("수정 권한이 없습니다.");
        }
        return new CommentResponseDto(comment);
    }

    @Transactional
    public void deleteComment(Long id, String userName,String userRole){
        Long commentId = id;
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다.")
        );
        if(comment.isEqualUserName(userName)||userRole.equals(UserRoleEnum.ADMIN.toString())){
            commentRepository.delete(comment);
        } else {
            throw new IllegalArgumentException("삭제 권한이 없습니다");
        }
    }
}
