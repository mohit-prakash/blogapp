package com.mps.blogapp.service;

import com.mps.blogapp.dto.CategoryDto;
import com.mps.blogapp.dto.PostDto;
import com.mps.blogapp.dto.PostResponse;
import com.mps.blogapp.entity.Category;

import java.util.List;

public interface IPostService {
    PostDto addPost(PostDto postDto,Long userId,Long catId);
    PostDto updatePost(PostDto postDto, Long postId,Long catId,Long userId);
    void removePost(Long postId,Long userId);
    PostDto getPost(Long postId);
    PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy,String sortDir);
    List<PostDto> getPostsByCatId(Long catId);
    List<PostDto> getPostsByUserId(Long userId);
    List<PostDto> searchPost(String keyword);
}
