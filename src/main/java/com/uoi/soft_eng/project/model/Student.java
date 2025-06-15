package com.uoi.soft_eng.project.model;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Student extends User {

    public Student(String username, String password, Role role){
        super.username = username;
        super.password = password;
        super.role=role;
    }

    @Column(name ="fullName")
    private String studentName;

    @Column(name = "AM", length = 20)
    private String AM; // Academic Registration Number
    
    @Column(name = "avgGrade")
    private Double avgGrade;

    @Column(name = "preferredLocation", length = 255)
    private String preferredLocation;

    @Column(name = "interests", columnDefinition = "TEXT")
    private String interests;

    @Column(name = "skills", columnDefinition = "TEXT")
    private String skills;

    @Column(name = "lookingForTraineeship")
    private boolean lookingForTraineeship = false;
    
    @OneToOne
    @JoinColumn(name = "assigned_traineeship_id", referencedColumnName = "id")
    private TraineeshipPosition assignedTraineeship;

}