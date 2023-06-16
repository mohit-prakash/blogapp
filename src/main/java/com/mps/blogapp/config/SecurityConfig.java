package com.mps.blogapp.config;

import com.mps.blogapp.constant.Roles;
import com.mps.blogapp.entity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeHttpRequests()
                .antMatchers("/api/v1/add","/api/v1/get/**","/api/v1/getall","/api/v1/remove/**","/api/v2/**").hasAuthority(Roles.ADMIN.name())
                .antMatchers("/api/v3/post/**","/api/v3/update/**","/api/v3/remove/**").hasAnyAuthority(Roles.ADMIN.name(),Roles.POST_ADMIN.name())
                .anyRequest().authenticated()
                .and()
                .httpBasic();
    }
}
