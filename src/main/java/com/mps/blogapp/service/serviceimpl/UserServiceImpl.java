package com.mps.blogapp.service.serviceimpl;

import com.mps.blogapp.config.PasswordEncoderConfig;
import com.mps.blogapp.constant.Roles;
import com.mps.blogapp.dto.UserDto;
import com.mps.blogapp.entity.User;
import com.mps.blogapp.entity.UserRole;
import com.mps.blogapp.exception.ResourceNotFoundException;
import com.mps.blogapp.repository.RoleRepository;
import com.mps.blogapp.repository.UserRepository;
import com.mps.blogapp.service.IUserService;
import com.mps.blogapp.utility.GenerateTempPwd;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService, UserDetailsService {
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoderConfig passwordEncoderConfig;
    @Autowired
    private GenerateTempPwd generateTempPwd;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDto addUser(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        user.getRoles().addAll(userDto.getRoleDtos().stream().map(roleDto -> modelMapper.map(roleDto,UserRole.class)).collect(Collectors.toList()));
        String pwd = generateTempPwd.genTempPwd();
        System.out.println("Password for "+user.getUsername()+" is: "+pwd);
        String encodedPwd = passwordEncoderConfig.passwordEncoder().encode(pwd);
        user.setPassword(encodedPwd);
        if (userDto.getRoleDtos().isEmpty()){
            UserRole userRole = new UserRole();
            userRole.setRole(Roles.VIEWER.name());
            user.getRoles().add(userRole);
        }
        userRepo.save(user);
        List<UserRole> roles = user.getRoles();
        for (UserRole role:roles){
            role.getUsers().add(user);
        }
        roleRepository.saveAll(roles);
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("This " + username + " not found."));
        return new org.springframework.security.core.userdetails.User(
          user.getUsername(),user.getPassword(),
                user.getRoles().stream()
                        .map(role->new SimpleGrantedAuthority(role.getRole())).collect(Collectors.toList()));
    }
}
