package com.mps.blogapp.controller;

import com.mps.blogapp.dto.UserDto;
import com.mps.blogapp.entity.User;
import com.mps.blogapp.exception.ResourceNotFoundException;
import com.mps.blogapp.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    @Autowired
    private IUserService userService;
    @PostMapping("/add")
    public ResponseEntity<UserDto> addUser(@Valid @RequestBody User user){
        UserDto addedUser = userService.addUser(user);
        return new ResponseEntity<>(addedUser, HttpStatus.CREATED);
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<String> updateUser(@Valid @RequestBody User user,@PathVariable Long userId){
        String message="";
        try {
           userService.updateUser(user, userId);
           message="User "+userId+" has been updated!!";
        }
        catch (ResourceNotFoundException rnfe){
            message= rnfe.getMessage();
        }
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping("/get/{userId}")
    public ResponseEntity<UserDto> findUser(@PathVariable Long userId){
        UserDto user = userService.getUser(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/getall")
    public ResponseEntity<List<UserDto>> findAllUsers(){
        List<UserDto> allUsers = userService.getAllUsers();
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }

    @DeleteMapping("/remove/{userId}")
    public ResponseEntity<String> removeUser(@PathVariable Long userId){
        try {
            userService.removeUser(userId);
            return new ResponseEntity<>("User removed!!", HttpStatus.OK);
        }
        catch (ResourceNotFoundException rnfe)
        {
            return new ResponseEntity<>(rnfe.getMessage(), HttpStatus.OK);
        }
    }
}
