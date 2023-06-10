package com.mps.blogapp.dto;

import com.mps.blogapp.entity.Category;
import com.mps.blogapp.entity.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
public class PostDto {
    private Long postId;
    private String postTitle;
    private String postDescription;
    private LocalDate postDate=LocalDate.now();
    private CategoryDto categoryDto;
    private UserDto userDto;
    private Set<CommentDto> commentDtos;
}
