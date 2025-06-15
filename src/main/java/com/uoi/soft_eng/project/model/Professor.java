package com.uoi.soft_eng.project.model;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
public class Professor extends User {
    
    public Professor(String username, String password, Role role){
        super.username = username;
        super.password = password;
        super.role=role;
    }
    
    @Column(name = "fullName")
    private String professorName;

    @Column(name="interests")
    private String interests;
    
    @OneToMany(mappedBy = "supervisor", cascade = CascadeType.ALL)
    private List<TraineeshipPosition> supervisedPositions;
}