package com.sparta.hanghaeblog.service;

import com.sparta.hanghaeblog.dto.CommentRequestDto;
import com.sparta.hanghaeblog.dto.CommentResponseDto;
import com.sparta.hanghaeblog.entity.Comment;
import com.sparta.hanghaeblog.entity.CommentLike;
import com.sparta.hanghaeblog.entity.Post;
import com.sparta.hanghaeblog.entity.PostLike;
import com.sparta.hanghaeblog.repository.CommentLikeRepository;
import com.sparta.hanghaeblog.repository.CommentRepository;
import com.sparta.hanghaeblog.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final CommentLikeRepository commentLikeRepository;

    @Transactional
    public CommentResponseDto addComment(Long postId, CommentRequestDto commentRequestDto, String username){
        String content = commentRequestDto.getContent();
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("해당 글이 존재하지 않습니다.")
        );
        Comment comment = new Comment(content, username, post.getId());
        commentRepository.saveAndFlush(comment);
        return new CommentResponseDto(comment);
    }

    @Transactional
    public CommentResponseDto updateMyComment(Long id, CommentRequestDto commentRequestDto, String username){
        String content = commentRequestDto.getContent();
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다.")
        );
        if(comment.isEqualUsername(username)){
            comment.updateContent(content);
            commentRepository.save(comment);
        } else {
            throw new IllegalArgumentException("수정 권한이 없습니다.");
        }
        return new CommentResponseDto(comment);
    }

    @Transactional
    public CommentResponseDto updateAdminComment(Long id, CommentRequestDto commentRequestDto){
        String content = commentRequestDto.getContent();
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다.")
        );
        comment.updateContent(content);
        commentRepository.save(comment);
        return new CommentResponseDto(comment);
    }

    @Transactional
    public void deleteMyComment(Long id, String username){
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다.")
        );
        if(comment.isEqualUsername(username)){
            commentRepository.delete(comment);
        } else {
            throw new IllegalArgumentException("삭제 권한이 없습니다");
        }
    }

    @Transactional
    public void deleteAdminComment(Long id){
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다.")
        );
        commentRepository.delete(comment);
    }

    @Transactional
    public String updateLikeComment(Long id, String username){
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 댓글이 존재 하지 않습니다.")
        );

        Optional<CommentLike> commentLike = commentLikeRepository.findByUsernameAndCommentId(username, id);
        if(commentLike.isPresent()) {
            comment.minusLikeCount();
            commentLikeRepository.deleteByUsernameAndComment(username, comment);
            return "Like -1";
        } else {
            comment.plusLikeCount();
            commentLikeRepository.save(new CommentLike(comment, username));
            return "Like +1";
        }
    }

}
