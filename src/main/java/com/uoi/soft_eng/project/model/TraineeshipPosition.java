package com.uoi.soft_eng.project.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "TraineeshipPosition")
public class TraineeshipPosition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "fromDate")
    private LocalDate fromDate;

    @Column(name = "toDate")
    private LocalDate toDate;

    @Column(name = "topics", columnDefinition = "TEXT")
    private String topics;

    @Column(name = "skills", columnDefinition = "TEXT")
    private String skills;

    @Column(name = "isAssigned")
    private boolean isAssigned;

    @Column(name = "studentLogbook", columnDefinition = "TEXT")
    private String studentLogbook;

    @Column(name = "passFailGrade")
    private boolean passFailGrade;

    // Mapping the foreign key relationship with the Student entity
    @OneToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private Student student;

    // Mapping the foreign key relationship with the Professor entity
    @ManyToOne
    @JoinColumn(name = "supervisor_id", referencedColumnName = "id")
    private Professor supervisor;

    // Mapping the foreign key relationship with the Company entity
    @ManyToOne
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    private Company company;

    // One-to-many relationship with the Evaluation entity
    @OneToMany(mappedBy = "traineeshipPosition", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Evaluation> evaluations = new ArrayList<>();  // Initialize the list to prevent NullPointerException
    
}