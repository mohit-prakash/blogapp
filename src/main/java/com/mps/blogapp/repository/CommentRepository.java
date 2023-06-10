package com.mps.blogapp.repository;

import com.mps.blogapp.entity.Comment;
import com.mps.blogapp.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findByPost(Post post);
    @Modifying
    @Query("Delete from Comment c where c.commentId=:commentId")
    void delete(Long commentId);
}
