package com.mps.blogapp.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
}
