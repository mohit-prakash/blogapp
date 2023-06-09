package com.mps.blogapp.repository;

import com.mps.blogapp.entity.Category;
import com.mps.blogapp.entity.Post;
import com.mps.blogapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {
    Post findByCategory(Category category);
    Post findByUser(User user);
}
