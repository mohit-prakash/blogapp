package com.mps.blogapp.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UserDto {
    private Long userId;
    private String username;
    private String userEmail;
}
