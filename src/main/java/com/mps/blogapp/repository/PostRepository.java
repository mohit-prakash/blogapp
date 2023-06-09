package com.mps.blogapp.repository;

import com.mps.blogapp.entity.Category;
import com.mps.blogapp.entity.Post;
import com.mps.blogapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findByCategory(Category category);
    List<Post> findByUser(User user);
}
