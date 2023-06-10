package com.mps.blogapp.service;

import com.mps.blogapp.dto.CategoryDto;
import com.mps.blogapp.dto.PostDto;
import com.mps.blogapp.entity.Category;

import java.util.List;

public interface IPostService {
    PostDto addPost(PostDto postDto,Long userId,Long catId);
    PostDto updatePost(PostDto postDto, Long postId,Long catId);
    void removePost(Long postId);
    PostDto getPost(Long postId);
    List<PostDto> getAllPost(Integer pageNumber, Integer pageSize);
    List<PostDto> getPostsByCatId(Long catId);
    List<PostDto> getPostsByUserId(Long userId);
}
