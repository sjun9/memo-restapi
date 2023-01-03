package com.sparta.hanghaeblog.repository;

import com.sparta.hanghaeblog.entity.CommentLike;
import com.sparta.hanghaeblog.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    Optional<CommentLike> findByUsername(String username);

    Optional<CommentLike> findByUsernameAndCommentId(String username, Long commentId);
}
