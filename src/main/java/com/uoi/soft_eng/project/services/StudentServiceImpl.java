package com.uoi.soft_eng.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uoi.soft_eng.project.model.Student;
import com.uoi.soft_eng.project.model.TraineeshipPosition;
import com.uoi.soft_eng.project.repository.StudentRepository;
import com.uoi.soft_eng.project.repository.TraineeshipPositionRepository;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;
    
    @Autowired
    private TraineeshipPositionRepository traineeshipPositionRepository;

    @Override
    public void saveProfile(Student student) {
        studentRepository.save(student);
    }

    @Override
    public Student retrieveProfile(String studentUsername) {
        return studentRepository.findByUsername(studentUsername)
                .orElse(null); // Return null if the student is not found
    }

    @Override
    public void saveLogbook(TraineeshipPosition position) {
        TraineeshipPosition original = traineeshipPositionRepository.findById(position.getId()).get();
        original.setStudentLogbook(position.getStudentLogbook());
        traineeshipPositionRepository.save(original);
    }
}
