package com.uoi.soft_eng.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.uoi.soft_eng.project.model.User;
import com.uoi.soft_eng.project.repository.UserRepository;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService, UserDetailsService  {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void saveUser(User user) {
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

    @Override
    public boolean isUserPresent(User user) {
        Optional<User> storedUser = userRepository.findByUsername(user.getUsername());
		return storedUser.isPresent();
    }

    @Override
    public User loadUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public User findById(String username) {
        // Find the user by their ID (username in this case)
        return userRepository.findById(username).orElse(null);
    }
}
