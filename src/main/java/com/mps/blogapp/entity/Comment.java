package com.mps.blogapp.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "comment_tab")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;
    @Column(length = 1000,nullable = false)
    private String commentContent;
    private LocalDate commentDate;
    @ManyToOne
    @JoinColumn(name = "user_id_fk")
    private User user;
    @ManyToOne
    @JoinColumn(name = "post_id_fk")
    private Post post;
}
