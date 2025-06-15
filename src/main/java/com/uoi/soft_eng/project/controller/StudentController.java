package com.uoi.soft_eng.project.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.uoi.soft_eng.project.model.Student;
import com.uoi.soft_eng.project.model.TraineeshipPosition;
import com.uoi.soft_eng.project.services.StudentService;


@Controller
public class StudentController {


    @Autowired
    private StudentService studentService;
    
    
    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null ? authentication.getName() : null;
    }
    
    
    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    @RequestMapping("/student/dashboard")
    public String getStudentDashboard(Model model) {
        Student retrievedStudent = studentService.retrieveProfile(getCurrentUsername());
       
        model.addAttribute("student", retrievedStudent); 
        return "student/dashboard";
    }


    @RequestMapping("/student/profile")
    public String retrieveProfile(Model model) {
        Student retrievedStudent = studentService.retrieveProfile(getCurrentUsername());
        model.addAttribute("student", retrievedStudent);        
        return "student/profile";
    }


    @RequestMapping("/student/save")
    public String saveProfile(@ModelAttribute("student") Student student, Model model) {
        studentService.saveProfile(student);
        model.addAttribute("successMessage", "Profile updated successfully!");
        return "student/dashboard";
    }

    @RequestMapping("/student/apply")
    public String applyForPosition(Model model) {
        Student retrievedStudent = studentService.retrieveProfile(getCurrentUsername());
        retrievedStudent.setLookingForTraineeship(!retrievedStudent.isLookingForTraineeship()); 
        studentService.saveProfile(retrievedStudent);
       
        model.addAttribute("student", retrievedStudent);
        model.addAttribute("successMessage", "Application status updated successfully!");
        return "student/dashboard";
    }

    @RequestMapping("/student/logbook")
    public String fillLogbook(Model model) {
        Student retrievedStudent = studentService.retrieveProfile(getCurrentUsername());
        model.addAttribute("position", retrievedStudent.getAssignedTraineeship());
        return "student/logbook";
    }

    @RequestMapping("/student/saveLogbook")
    public String saveLogbook(@ModelAttribute("position")TraineeshipPosition position, Model model) {
        studentService.saveLogbook(position);
        
        Student retrievedStudent = studentService.retrieveProfile(getCurrentUsername());
        model.addAttribute("student", retrievedStudent);
        model.addAttribute("successMessage", "Logbook updated successfully!");
        return "student/dashboard";
    }
}
