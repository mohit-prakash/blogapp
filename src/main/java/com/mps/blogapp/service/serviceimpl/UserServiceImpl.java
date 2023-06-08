package com.mps.blogapp.service.serviceimpl;

import com.mps.blogapp.dto.UserDto;
import com.mps.blogapp.entity.User;
import com.mps.blogapp.exception.ResourceNotFoundException;
import com.mps.blogapp.repository.UserRepository;
import com.mps.blogapp.service.IUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDto addUser(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        userRepo.save(user);
        return modelMapper.map(user,UserDto.class);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Long userId) {
        User user1 = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with this id " + userId));
        user1.setUserEmail(userDto.getUserEmail());
        user1.setUsername(userDto.getUsername());
        userRepo.save(user1);
        return modelMapper.map(user1,UserDto.class);
    }

    @Override
    public void removeUser(Long userId) {
        UserDto userDto = getUser(userId);
        User user = modelMapper.map(userDto,User.class);
        userRepo.delete(user);
    }

    @Override
    public UserDto getUser(Long userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with this id " + userId));
        return modelMapper.map(user,UserDto.class);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> userList = userRepo.findAll();
        List<UserDto> userDtos = userList.stream().map((user) -> modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
        return userDtos;
    }
}
