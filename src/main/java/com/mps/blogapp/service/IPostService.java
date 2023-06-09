package com.mps.blogapp.service;

import com.mps.blogapp.dto.CategoryDto;
import com.mps.blogapp.dto.PostDto;
import com.mps.blogapp.entity.Category;

import java.util.List;

public interface IPostService {
    PostDto addPost(PostDto postDto,Long userId,Long catId);
    PostDto updatePost(PostDto postDto, Long postId);
    void removePost(Long postId);
    PostDto getPost(Long postId);
    List<PostDto> getAllPost();
    PostDto getPostByCatId(Long catId);
    PostDto getPostByUserId(Long userId);
}
