package com.uoi.soft_eng.project.services;

import com.uoi.soft_eng.project.model.Student;
import com.uoi.soft_eng.project.model.TraineeshipPosition;

public interface StudentService {

    void saveProfile(Student student);

    Student retrieveProfile(String studentUsername);

    void saveLogbook(TraineeshipPosition position);
    
}
