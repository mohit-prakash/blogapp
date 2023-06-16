package com.mps.blogapp.repository;

import com.mps.blogapp.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<UserRole,Long> {
}
