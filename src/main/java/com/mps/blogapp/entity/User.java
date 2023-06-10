package com.mps.blogapp.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="user_tab")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String username;
    private String userEmail;
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Post> posts=new ArrayList<>();
    //one user can do multiple post, hence one to many
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private Set<Comment> comments=new HashSet<>();
}
