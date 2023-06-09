package com.mps.blogapp.entity;

import lombok.Data;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "category_tab")
@Data
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "catId", nullable = false)
    private Long catId;
    @Column(name = "catTitle")
    private String catTitle;
    private String catDescription;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "category")
    private List<Post> posts = new ArrayList<>();
}