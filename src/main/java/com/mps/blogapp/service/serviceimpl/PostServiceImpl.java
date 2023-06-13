package com.mps.blogapp.service.serviceimpl;

import com.mps.blogapp.dto.*;
import com.mps.blogapp.entity.Category;
import com.mps.blogapp.entity.Post;
import com.mps.blogapp.entity.User;
import com.mps.blogapp.exception.ResourceNotFoundException;
import com.mps.blogapp.repository.CategoryRepository;
import com.mps.blogapp.repository.CommentRepository;
import com.mps.blogapp.repository.PostRepository;
import com.mps.blogapp.repository.UserRepository;
import com.mps.blogapp.service.ICategoryService;
import com.mps.blogapp.service.IPostService;
import com.mps.blogapp.service.IUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
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
    private CommentRepository commentRepository;
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
        postDto.setPostId(savedPost.getPostId());
        return postDto;
    }

    @Override
    public PostDto updatePost(PostDto postDto, Long postId,Long catId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found with this id " + postId));
        Category category = categoryRepository.findById(catId).orElseThrow(() -> new ResourceNotFoundException("Category " + catId + " not found!!"));
        post.setPostTitle(postDto.getPostTitle());
        post.setPostDate(postDto.getPostDate());
        post.setPostDescription(postDto.getPostDescription());
        post.setCategory(category);
        postRepository.save(post);
        PostDto postDto1 = modelMapper.map(post, PostDto.class);
        postDto1.setCategoryDto(modelMapper.map(category, CategoryDto.class));
        postDto1.setUserDto(modelMapper.map(post.getUser(), UserDto.class));
        return postDto1;
    }

    @Override
    public void removePost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found with this id " + postId));
        postRepository.delete(post);
    }

    @Override
    public PostDto getPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found with this id " + postId));
        PostDto postDto = modelMapper.map(post, PostDto.class);
        postDto.setCategoryDto(modelMapper.map(post.getCategory(), CategoryDto.class));
        postDto.setUserDto(modelMapper.map(post.getUser(), UserDto.class));
        Set<CommentDto> commentDtos = commentRepository.findByPost(post).stream()
                .map(comment -> {
                    CommentDto commentDto = modelMapper.map(comment, CommentDto.class);
                    commentDto.setUserDto(modelMapper.map(comment.getUser(), UserDto.class));
                    return commentDto;
                })
                .collect(Collectors.toSet());
        postDto.setCommentDtos(commentDtos);
        return postDto;
    }

    @Override
    public PostResponse getAllPost(Integer pageNumber, Integer pageSize,String sortBy,String sortDir) {
        Sort sort=null;
        if (("dsc").equalsIgnoreCase(sortDir)){
            sort = Sort.by(sortBy).descending();
        }else {
            sort = Sort.by(sortBy).ascending();
        }
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        Page<Post> postPage = postRepository.findAll(pageable);
        List<Post> posts = postPage.getContent();
        List<CategoryDto> categoryDtos = posts.stream().map(post -> modelMapper.map(post.getCategory(), CategoryDto.class)).collect(Collectors.toList());
        List<UserDto> userDtos = posts.stream().map(post -> modelMapper.map(post.getUser(), UserDto.class)).collect(Collectors.toList());
        List<PostDto> postDtoList = posts.stream().map(post -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        int i=0;
        for(PostDto postDto:postDtoList){
            postDto.setCategoryDto(categoryDtos.get(i));
            postDto.setUserDto(userDtos.get(i++));
            Set<CommentDto> commentDtos = commentRepository.findByPost(
                            modelMapper.map(postDto, Post.class))
                    .stream().collect(Collectors.toSet())
                    .stream().map(comment ->
                            {
                                CommentDto commentDto = modelMapper.map(comment, CommentDto.class);
                                commentDto.setUserDto(modelMapper.map(comment.getUser(), UserDto.class));
                                return commentDto;
                            })
                    .collect(Collectors.toSet());
            postDto.setCommentDtos(commentDtos);
        }
        PostResponse postResponse = new PostResponse();
        postResponse.setPostDtos(postDtoList);
        postResponse.setPageNumber(postPage.getNumber());
        postResponse.setPageSize(postPage.getSize());
        postResponse.setTotalElements(postPage.getTotalElements());
        postResponse.setTotalPages(postPage.getTotalPages());
        postResponse.setIsLastPage(postPage.isLast());
        return postResponse;
    }

    @Override
    public List<PostDto> getPostsByCatId(Long catId) {
        Category category = categoryRepository.findById(catId).orElseThrow(()->new ResourceNotFoundException("Category "+catId+" not found!!"));
        List<Post> posts = postRepository.findByCategory(category);
        List<PostDto> postDtos = posts.stream().map(post -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        int i=0;
        for (PostDto postDto: postDtos){
            postDto.setCategoryDto(modelMapper.map(category, CategoryDto.class));
            postDto.setUserDto(modelMapper.map(posts.get(i++).getUser(), UserDto.class));
        }
        return postDtos;
    }

    @Override
    public List<PostDto> getPostsByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User "+userId+" not found!!"));
        List<Post> posts = postRepository.findByUser(user);
        List<PostDto> postDtos = posts.stream().map(post -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        int i=0;
        for (PostDto postDto:postDtos){
            postDto.setUserDto(modelMapper.map(user, UserDto.class));
            postDto.setCategoryDto(modelMapper.map(posts.get(i++).getCategory(), CategoryDto.class));
        }
        return postDtos;
    }

    @Override
    public List<PostDto> searchPost(String keyword) {
        List<Post> posts = postRepository.findByPostTitleContaining(keyword);
        List<PostDto> postDtos = posts.stream().map(post -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        int i=0;
        for (PostDto postDto:postDtos){
            postDto.setCategoryDto(modelMapper.map(posts.get(i).getCategory(), CategoryDto.class));
            postDto.setUserDto(modelMapper.map(posts.get(i++).getUser(), UserDto.class));
        }
        return postDtos;
    }
}
