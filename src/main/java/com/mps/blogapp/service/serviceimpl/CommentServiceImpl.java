package com.mps.blogapp.service.serviceimpl;

import com.mps.blogapp.dto.CommentDto;
import com.mps.blogapp.dto.PostDto;
import com.mps.blogapp.dto.UserDto;
import com.mps.blogapp.entity.Comment;
import com.mps.blogapp.entity.Post;
import com.mps.blogapp.entity.User;
import com.mps.blogapp.exception.NotValidUser;
import com.mps.blogapp.exception.ResourceNotFoundException;
import com.mps.blogapp.repository.CommentRepository;
import com.mps.blogapp.repository.PostRepository;
import com.mps.blogapp.repository.UserRepository;
import com.mps.blogapp.service.ICommentService;
import com.mps.blogapp.service.IPostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class CommentServiceImpl implements ICommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    @Transactional
    public CommentDto addComment(CommentDto commentDto, Long userId, Long postId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User " + userId + " not found!!"));
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post " + postId + " not found!!"));
        commentDto.setUserDto(modelMapper.map(user, UserDto.class));
        Comment comment = modelMapper.map(commentDto, Comment.class);
        comment.setPost(post);
        Comment savedComment = commentRepository.save(comment);
        commentDto.setCommentId(savedComment.getCommentId());
        return commentDto;
    }

    @Override
    @Transactional
    public CommentDto updateComment(CommentDto commentDto,Long commentId, Long postId,Long userId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post " + postId + " not found"));
        Set<Comment> comments = post.getComments();
        Comment comment=null;
        boolean isCommentFound=false;
        boolean isValidUser=false;
        for (Comment c : comments){
            if(c.getCommentId()==commentId){
                isCommentFound=true;
                if (c.getUser().getUserId()==userId){
                    isValidUser=true;
                    comment=c;
                    break;
                }
            }
        }
        if (!isCommentFound){
            throw new ResourceNotFoundException("Comment "+commentId+" not found.");
        } else if (!isValidUser) {
            throw new NotValidUser("User "+userId+" is not a valid user to edit this comment.");
        }
        Comment cmnt = modelMapper.map(commentDto, Comment.class);
        cmnt.setCommentId(commentId);
        cmnt.setUser(comment.getUser());
        cmnt.setPost(comment.getPost());
        commentRepository.save(cmnt);
        CommentDto cmntDto = modelMapper.map(cmnt, CommentDto.class);
        cmntDto.setUserDto(modelMapper.map(cmnt.getUser(), UserDto.class));
        return cmntDto;
    }

    @Override
    @Transactional
    public void removeComment(Long commentId,Long postId,Long userId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post " + postId + " not found"));
        Set<Comment> comments = post.getComments();
        Comment comment=null;
        boolean isCommentFound=false;
        boolean isValidUser=false;
        for (Comment c : comments){
            if(c.getCommentId()==commentId){
                isCommentFound=true;
                if (c.getUser().getUserId()==userId){
                    isValidUser=true;
                    comment=c;
                    break;
                }
            }
        }
        if (!isCommentFound){
            throw new ResourceNotFoundException("Comment "+commentId+" not found.");
        } else if (!isValidUser) {
            throw new NotValidUser("User "+userId+" is not a valid user to remove this comment.");
        }
        commentRepository.delete(comment.getCommentId());
    }
}
