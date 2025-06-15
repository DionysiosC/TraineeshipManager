package com.uoi.soft_eng.project.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uoi.soft_eng.project.factory.PositionsSearchFactory;
import com.uoi.soft_eng.project.factory.SupervisorAssignmentFactory;
import com.uoi.soft_eng.project.model.Professor;
import com.uoi.soft_eng.project.model.Student;
import com.uoi.soft_eng.project.model.TraineeshipPosition;
import com.uoi.soft_eng.project.repository.StudentRepository;
import com.uoi.soft_eng.project.repository.TraineeshipPositionRepository;
import com.uoi.soft_eng.project.strategy.assignments.SupervisorAssignmentStrategy;
import com.uoi.soft_eng.project.strategy.positions.PositionsSearchStrategy;

@Service
public class CommitteeServiceImpl implements CommitteeService{

    @Autowired
    private TraineeshipPositionRepository traineeshipPositionRepository;
    
    @Autowired
    private StudentRepository studentRepository;
    
    @Autowired
    private PositionsSearchFactory positionsSearchFactory;

    @Autowired
    private SupervisorAssignmentFactory supervisorAssignmentFactory;

    @Override
    public List<TraineeshipPosition> retrievePositionsForApplicant(String applicantUsername, String strategy) {
        PositionsSearchStrategy positionsSearchStrategy = positionsSearchFactory.create(strategy);
        return positionsSearchStrategy.search(applicantUsername);
    }
    
    @Override
    public List<Student> retrieveTraineeshipApplications(){
        return studentRepository.retrieveTraineeshipApplications();
    }
    
    @Override
    public void assignPosition(Integer positionId, String studentUsername){
        Optional <TraineeshipPosition> positionOpt = traineeshipPositionRepository.findById(positionId);
        Optional <Student> studentOpt = studentRepository.findByUsername(studentUsername);
        if (positionOpt.isPresent() && studentOpt.isPresent()){
               TraineeshipPosition position = positionOpt.get();
               Student student = studentOpt.get();
               position.setAssigned(true);
               position.setStudent(student);
               traineeshipPositionRepository.save(position);
               
               student.setAssignedTraineeship(position);
               student.setLookingForTraineeship(false);
               studentRepository.save(student);
        }
    }   
    
    @Override
    public void assignSupervisor(Integer positionId, String strategy) throws IllegalStateException{
        SupervisorAssignmentStrategy supervisorAssignmentStrategy = supervisorAssignmentFactory.create(strategy);
        supervisorAssignmentStrategy.assign(positionId);
    }
    
    @Override
    public List<TraineeshipPosition> listAssignedTraineeships(){
        return traineeshipPositionRepository.returnAllAssignedPositions();   
    }
    
    @Override
    public void completeAssignedTraineeships(Integer positionId, Double finalScore){
        Optional <TraineeshipPosition> positionOpt = traineeshipPositionRepository.findById(positionId);
        TraineeshipPosition position = null;
        if (!positionOpt.isPresent())
            return;
        position = positionOpt.get();
        Student intern = position.getStudent();
        
        if (finalScore > 5.0)
            position.setPassFailGrade(true);
        
        intern.setAvgGrade(finalScore);
        
        studentRepository.save(intern);
        traineeshipPositionRepository.save(position);
    } 
}
