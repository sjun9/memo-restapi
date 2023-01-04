package com.sparta.hanghaeblog.repository;

import com.sparta.hanghaeblog.entity.Comment;
import com.sparta.hanghaeblog.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {

    Optional<CommentLike> findByUsernameAndCommentId(String username, Long commentId);

    void deleteByUsernameAndComment(String username, Comment comment);
}
