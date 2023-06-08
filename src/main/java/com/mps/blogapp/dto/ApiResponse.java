package com.mps.blogapp.dto;

import lombok.*;

@AllArgsConstructor
@Data
public class ApiResponse {
    private String message;
    private Boolean isSuccess;
}
