package com.uoi.soft_eng.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.uoi.soft_eng.project.model.Company;
import com.uoi.soft_eng.project.model.Professor;
import com.uoi.soft_eng.project.model.Student;
import com.uoi.soft_eng.project.model.User;
import com.uoi.soft_eng.project.services.UserService;

@Controller
public class AuthController {
    
    @Autowired
    UserService userService;
    
    @RequestMapping("/login")
    public String login(){
        return "auth/signin";
    }

    @RequestMapping("/register")
    public String register(Model model){
        model.addAttribute("user", new User());
        return "auth/signup";
    }

    @RequestMapping("/save")
    public String registerUser(@ModelAttribute("user") User user, Model model) {
        
        if (userService.loadUserByUsername(user.getUsername()) != null ) {
            model.addAttribute("successMessage", "User already registered!");
            return "auth/signin";
        }
        User newUser = null;
        switch (user.getRole()) {
            case STUDENT:
                newUser = new Student(user.getUsername(), user.getPassword(), user.getRole());
                break;
            case PROFESSOR:
                newUser = new Professor(user.getUsername(), user.getPassword(), user.getRole());
                break;
            case COMPANY:
                newUser = new Company(user.getUsername(), user.getPassword(), user.getRole());
                break;
            case COMMITTEE:
                newUser = new User(user.getUsername(), user.getPassword(), user.getRole());
                break;
        }
        userService.saveUser(newUser);
        
    
        model.addAttribute("successMessage", "User registered successfully!");
        return "auth/signin";
    }

}
