package com.mps.blogapp.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "post_tab")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;
    @Column(nullable = false)
    private String postTitle;
    @Column(length = 1000)
    private String postDescription;
    private LocalDate postDate;
    @ManyToOne
    @JoinColumn(name = "catIdFk") //To change the join column name
    private Category category;
    @ManyToOne
    private User user;
    //without user and category post cannot be created
}
