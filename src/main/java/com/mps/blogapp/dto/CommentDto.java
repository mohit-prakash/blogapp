package com.mps.blogapp.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CommentDto {
    private Long commentId;
    private String commentContent;
    private LocalDate commentDate = LocalDate.now();
    private UserDto userDto;
}
