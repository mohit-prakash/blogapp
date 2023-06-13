package com.mps.blogapp.controller;

import com.mps.blogapp.dto.ApiResponse;
import com.mps.blogapp.dto.CommentDto;
import com.mps.blogapp.exception.NotValidUser;
import com.mps.blogapp.exception.ResourceNotFoundException;
import com.mps.blogapp.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v4")
public class CommentController {
    @Autowired
    private ICommentService commentService;
    @PostMapping("/add/user/{userId}/post/{postId}")
    public ResponseEntity<?> postComment(@PathVariable Long userId, @PathVariable Long postId, @RequestBody CommentDto commentDto){
        try {
            CommentDto addedCommentDto = commentService.addComment(commentDto, userId, postId);
            return new ResponseEntity<>(addedCommentDto, HttpStatus.CREATED);
        }catch (ResourceNotFoundException rnfe){
            return new ResponseEntity<>(new ApiResponse(rnfe.getMessage(), false),HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/edit/post/{postId}/comment/{cmntId}/user/{userId}")
    public ResponseEntity<?> editComment(@PathVariable Long cmntId,@PathVariable Long postId,@PathVariable Long userId,@RequestBody CommentDto commentDto){
        try {
            CommentDto updatedCommentDto = commentService.updateComment(commentDto, cmntId, postId,userId);
            return new ResponseEntity<>(updatedCommentDto,HttpStatus.CREATED);
        }catch (ResourceNotFoundException rnfe){
            return new ResponseEntity<>(new ApiResponse(rnfe.getMessage(), false),HttpStatus.NOT_FOUND);
        } catch (NotValidUser nvu){
            return new ResponseEntity<>(new ApiResponse(nvu.getMessage(), false),HttpStatus.UNAUTHORIZED);
        }
    }
    @DeleteMapping("/remove/post/{postId}/comment/{cmntId}/user/{userId}")
    public ResponseEntity<ApiResponse> removeComment(@PathVariable Long cmntId,@PathVariable Long userId,@PathVariable Long postId){
        try {
            commentService.removeComment(cmntId,postId,userId);
            return new ResponseEntity<>(new ApiResponse("Comment "+cmntId+" deleted.",true),HttpStatus.OK);
        }catch (ResourceNotFoundException rnfe){
            return new ResponseEntity<>(new ApiResponse(rnfe.getMessage(), false),HttpStatus.NOT_FOUND);
        } catch (NotValidUser nvu){
            return new ResponseEntity<>(new ApiResponse(nvu.getMessage(), false),HttpStatus.UNAUTHORIZED);
        }
    }

}
