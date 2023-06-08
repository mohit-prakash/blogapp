package com.mps.blogapp.dto;

import lombok.Data;
import javax.validation.constraints.Size;

@Data
public class CategoryDto {
    private Long catId;
    @Size(min=3,message = "Title should be greater than 3 characters")
    private String catTitle;
    private String catDescription;
}
