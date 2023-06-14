package com.mps.blogapp.controller;

import com.mps.blogapp.dto.ApiResponse;
import com.mps.blogapp.dto.PostDto;
import com.mps.blogapp.dto.PostResponse;
import com.mps.blogapp.exception.NotValidUser;
import com.mps.blogapp.exception.ResourceNotFoundException;
import com.mps.blogapp.service.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.mps.blogapp.constant.Constant.*;

@RestController
@RequestMapping("/api/v3")
public class PostController {
    @Autowired
    private IPostService postService;
    @PostMapping("/post/user/{userId}/category/{catId}")
    public ResponseEntity<?> addPost(@RequestBody PostDto postDto,@PathVariable Long userId,@PathVariable Long catId){
        try {
            PostDto addedPostDto = postService.addPost(postDto, userId, catId);
            return new ResponseEntity<>(addedPostDto, HttpStatus.CREATED);
        }catch (ResourceNotFoundException rnfe){
            return new ResponseEntity<>(rnfe.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("update/post/{postId}/category/{catId}/user/{userId}")
    public ResponseEntity<?> updatePost(@RequestBody PostDto postDto, @PathVariable Long postId,@PathVariable Long catId, @PathVariable Long userId){
        try {
            PostDto updatedPostDto = postService.updatePost(postDto, postId,catId,userId);
            return new ResponseEntity<>(updatedPostDto, HttpStatus.CREATED);
        }catch (ResourceNotFoundException rnfe){
            return new ResponseEntity<>(new ApiResponse(rnfe.getMessage(), false), HttpStatus.NOT_FOUND);
        }catch (NotValidUser nvu){
            return new ResponseEntity<>(new ApiResponse(nvu.getMessage(), false),HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping("/remove/{postId}/user/{userId}")
    public ResponseEntity<ApiResponse> removePost(@PathVariable Long postId,@PathVariable Long userId){
        try{
            postService.removePost(postId,userId);
            return new ResponseEntity<>(new ApiResponse("Post "+postId+" removed!!",true),HttpStatus.OK);
        }catch (ResourceNotFoundException rnfe){
            return new ResponseEntity<>(new ApiResponse(rnfe.getMessage(), false),HttpStatus.NOT_FOUND);
        }catch (NotValidUser nvu){
            return new ResponseEntity<>(new ApiResponse(nvu.getMessage(), false),HttpStatus.UNAUTHORIZED);
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
    public ResponseEntity<PostResponse> getAllPosts(@RequestParam(defaultValue = PAGE_NUMBER,required = false) Integer pageNumber,
                                                    @RequestParam(defaultValue = PAGE_SIZE,required = false) Integer pageSize,
                                                    @RequestParam(defaultValue = SORT_BY,required = false) String sortBy,
                                                    @RequestParam(defaultValue = SORT_DIR,required = false)String sortDir){
        return new ResponseEntity<>(postService.getAllPost(pageNumber,pageSize,sortBy,sortDir),HttpStatus.OK);
    }

    @GetMapping("/getbycategory")
    public ResponseEntity<?> getPostByCategory(@RequestParam(name = "catId") Long catId){
        try {
            List<PostDto> postDtos = postService.getPostsByCatId(catId);
            return new ResponseEntity<>(postDtos,HttpStatus.OK);
        } catch (ResourceNotFoundException rnfe){
            return new ResponseEntity<>(new ApiResponse(rnfe.getMessage(), false),HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getbyuser")
    public ResponseEntity<?> getPostByUser(@RequestParam(name = "userId") Long userId){
        try {
            List<PostDto> postDtos = postService.getPostsByUserId(userId);
            return new ResponseEntity<>(postDtos,HttpStatus.OK);
        } catch (ResourceNotFoundException rnfe){
            return new ResponseEntity<>(new ApiResponse(rnfe.getMessage(), false),HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<PostDto>> getPostsUsingKeyword(@PathVariable String keyword){
        List<PostDto> postDtos = postService.searchPost(keyword);
        return new ResponseEntity<>(postDtos,HttpStatus.OK);
    }
}
