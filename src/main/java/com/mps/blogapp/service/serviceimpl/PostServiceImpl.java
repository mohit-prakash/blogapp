package com.mps.blogapp.service.serviceimpl;

import com.mps.blogapp.dto.CategoryDto;
import com.mps.blogapp.dto.PostDto;
import com.mps.blogapp.dto.UserDto;
import com.mps.blogapp.entity.Category;
import com.mps.blogapp.entity.Post;
import com.mps.blogapp.entity.User;
import com.mps.blogapp.exception.ResourceNotFoundException;
import com.mps.blogapp.repository.CategoryRepository;
import com.mps.blogapp.repository.PostRepository;
import com.mps.blogapp.repository.UserRepository;
import com.mps.blogapp.service.ICategoryService;
import com.mps.blogapp.service.IPostService;
import com.mps.blogapp.service.IUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements IPostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepository userRepository;
    @Override
    public PostDto addPost(PostDto postDto,Long userId,Long catId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User " + userId + " not found!!"));
        Category category = categoryRepository.findById(catId).orElseThrow(() -> new ResourceNotFoundException("Category " + catId + " not found!!"));
        postDto.setCategoryDto(modelMapper.map(category, CategoryDto.class));
        postDto.setUserDto(modelMapper.map(user, UserDto.class));
        Post post = modelMapper.map(postDto, Post.class);
        Post savedPost = postRepository.save(post);
        return modelMapper.map(savedPost, PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found with this id " + postId));
        post.setPostTitle(postDto.getPostTitle());
        post.setPostDate(postDto.getPostDate());
        post.setPostDescription(postDto.getPostDescription());
        postRepository.save(post);
        return modelMapper.map(post, PostDto.class);
    }

    @Override
    public void removePost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found with this id " + postId));
        postRepository.delete(post);
    }

    @Override
    public PostDto getPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found with this id " + postId));
        return modelMapper.map(post, PostDto.class);
    }

    @Override
    public List<PostDto> getAllPost() {
        List<Post> posts = postRepository.findAll();
        List<PostDto> postDtoList = posts.stream().map(post -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        return postDtoList;
    }

    @Override
    public PostDto getPostByCatId(Long catId) {
        Category category = categoryRepository.findById(catId).orElseThrow(()->new ResourceNotFoundException("Category "+catId+" not found!!"));
        Post post = postRepository.findByCategory(category);
        return modelMapper.map(post, PostDto.class);
    }

    @Override
    public PostDto getPostByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User "+userId+" not found!!"));
        Post post = postRepository.findByUser(user);
        return modelMapper.map(post, PostDto.class);
    }
}
