package com.mps.blogapp.service;

import com.mps.blogapp.dto.UserDto;
import com.mps.blogapp.entity.User;

import java.util.List;

public interface IUserService {
    UserDto addUser(UserDto userDto);
    UserDto updateUser(UserDto userDto, Long userId);
    void removeUser(Long userId);
    UserDto getUser(Long id);
    List<UserDto> getAllUsers();
}
