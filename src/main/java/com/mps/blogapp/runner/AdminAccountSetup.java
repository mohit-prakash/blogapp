package com.mps.blogapp.runner;

import com.mps.blogapp.constant.Roles;
import com.mps.blogapp.dto.RoleDto;
import com.mps.blogapp.dto.UserDto;
import com.mps.blogapp.entity.User;
import com.mps.blogapp.entity.UserRole;
import com.mps.blogapp.repository.UserRepository;
import com.mps.blogapp.service.IUserService;
import com.mps.blogapp.utility.GenerateTempPwd;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminAccountSetup implements CommandLineRunner {

    @Value("${admin.username}")
    private String username;
    @Value("${admin.email}")
    private String email;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private IUserService userService;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public void run(String... args) throws Exception {
        if (!userRepository.findByUsername(username).isPresent()){
            User user = new User();
            user.setUsername(username);
            user.setUserEmail(email);
            UserRole userRole = new UserRole();
            userRole.setRole(Roles.ADMIN.name());
            user.getRoles().add(userRole);
            RoleDto roleDto = modelMapper.map(userRole, RoleDto.class);
//            String pwd = generateTempPwd.genTempPwd();
//            System.out.println("Admin=> username: "+username+" Password: "+pwd);
//            pwd=passwordEncoder.encode(pwd);
//            user.setPassword(pwd);
            UserDto userDto = modelMapper.map(user, UserDto.class);
            userDto.getRoleDtos().add(roleDto);
            userService.addUser(userDto);
        }
    }
}
