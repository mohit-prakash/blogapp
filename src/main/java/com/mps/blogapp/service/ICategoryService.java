package com.mps.blogapp.service;

import com.mps.blogapp.dto.CategoryDto;

import java.util.List;

public interface ICategoryService {
    CategoryDto addCategory(CategoryDto categoryDto);
    CategoryDto updateCategory(CategoryDto categoryDto,Long catId);
    void removeCategory(Long catId);
    CategoryDto getOneCategory(Long catId);
    List<CategoryDto> getAllCategories();
}
