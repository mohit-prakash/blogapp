package com.mps.blogapp.service;

import com.mps.blogapp.dto.CommentDto;

public interface ICommentService {
    CommentDto addComment(CommentDto commentDto,Long userId, Long postId);
    CommentDto updateComment(CommentDto commentDto,Long commentId,Long postId,Long userId);
    void removeComment(Long commentId,Long postId,Long userId);
}
