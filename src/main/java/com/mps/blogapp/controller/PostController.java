package com.mps.blogapp.controller;

import com.mps.blogapp.dto.ApiResponse;
import com.mps.blogapp.dto.PostDto;
import com.mps.blogapp.exception.ResourceNotFoundException;
import com.mps.blogapp.service.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v3")
public class PostController {
    @Autowired
    private IPostService postService;
    @PostMapping("/post/user/{userId}/category/{catId}")
    public ResponseEntity<PostDto> addPost(@RequestBody PostDto postDto,@PathVariable Long userId,@PathVariable Long catId){
        PostDto addedPostDto = postService.addPost(postDto,userId,catId);
        return new ResponseEntity<>(addedPostDto, HttpStatus.CREATED);
    }
    @PutMapping("update/{postId}")
    public ResponseEntity<?> updatePost(@RequestBody PostDto postDto, @PathVariable Long postId){
        try {
            PostDto updatedPostDto = postService.updatePost(postDto, postId);
            return new ResponseEntity<>(updatedPostDto, HttpStatus.CREATED);
        }catch (ResourceNotFoundException rnfe){
            return new ResponseEntity<>(rnfe.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/remove/{postId}")
    public ResponseEntity<ApiResponse> removePost(@PathVariable Long postId){
        try{
            postService.removePost(postId);
            return new ResponseEntity<>(new ApiResponse("Post "+postId+" removed!!",true),HttpStatus.OK);
        }catch (ResourceNotFoundException rnfe){
            return new ResponseEntity<>(new ApiResponse(rnfe.getMessage(), false),HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/get/{postId}")
    public ResponseEntity<?> getPost(@PathVariable Long postId){
        try {
            PostDto postDto = postService.getPost(postId);
            return new ResponseEntity<>(postDto,HttpStatus.OK);
        }catch (ResourceNotFoundException rnfe){
            return new ResponseEntity<>(new ApiResponse(rnfe.getMessage(), false),HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getall")
    public ResponseEntity<List<PostDto>> getAllPosts(){
        return new ResponseEntity<>(postService.getAllPost(),HttpStatus.OK);
    }

    @GetMapping("/getbycategory")
    public ResponseEntity<?> getPostByCategory(@RequestParam(name = "catId") Long catId){
        try {
            PostDto postDto = postService.getPostByCatId(catId);
            return new ResponseEntity<>(postDto,HttpStatus.OK);
        } catch (ResourceNotFoundException rnfe){
            return new ResponseEntity<>(new ApiResponse(rnfe.getMessage(), false),HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getbyuser")
    public ResponseEntity<?> getPostByUser(@RequestParam(name = "userId") Long userId){
        try {
            PostDto postDto = postService.getPostByUserId(userId);
            return new ResponseEntity<>(postDto,HttpStatus.OK);
        } catch (ResourceNotFoundException rnfe){
            return new ResponseEntity<>(new ApiResponse(rnfe.getMessage(), false),HttpStatus.NOT_FOUND);
        }
    }
}
