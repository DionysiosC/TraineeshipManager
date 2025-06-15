package com.uoi.soft_eng.project.services;

import org.springframework.security.core.userdetails.UserDetails;

import com.uoi.soft_eng.project.model.User;

public interface UserService {
    void saveUser(User user);
    boolean isUserPresent(User user);
    UserDetails loadUserByUsername(String username);
    User findById(String username);
}
