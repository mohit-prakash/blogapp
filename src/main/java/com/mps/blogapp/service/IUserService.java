package com.mps.blogapp.service;

import com.mps.blogapp.dto.UserDto;
import com.mps.blogapp.entity.User;

import java.util.List;

public interface IUserService {
    UserDto addUser(User user);
    UserDto updateUser(User user, Long userId);
    void removeUser(Long userId);
    UserDto getUser(Long id);
    List<UserDto> getAllUsers();
}
