package com.mps.blogapp.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name="user_tab")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @NotEmpty
    @Size(min = 3,message = "Size should be atleast 3")
    private String username;
    @Pattern(regexp = "^[a-z0-9]{3,10}[/@][a-z]{3,10}[.][a-z]{3,7}$",message = "Email should be in proper format!!")
    private String userEmail;
}
