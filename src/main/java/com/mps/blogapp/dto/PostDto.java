package com.mps.blogapp.dto;

import com.mps.blogapp.entity.Category;
import com.mps.blogapp.entity.User;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PostDto {
    private String postTitle;
    private String postDescription;
    private LocalDate postDate=LocalDate.now();
    private CategoryDto categoryDto;
    private UserDto userDto;
}
