package com.uoi.soft_eng.project.strategy.assignments;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.uoi.soft_eng.project.model.Professor;
import com.uoi.soft_eng.project.model.TraineeshipPosition;
import com.uoi.soft_eng.project.repository.ProfessorRepository;
import com.uoi.soft_eng.project.repository.TraineeshipPositionRepository;

@Component
public class AssignmentBasedOnLoad implements SupervisorAssignmentStrategy{

    @Autowired
    private ProfessorRepository professorRepository; 
    
    @Autowired
    private TraineeshipPositionRepository traineeshipPositionRepository;

        
    @Override
    public void assign(Integer positionID){
        TraineeshipPosition position = traineeshipPositionRepository.findById(positionID).orElse(null);
        List<Professor> professors = professorRepository.findAll();
        
        Professor bestMatch = null;
        int lowestLoad = Integer.MAX_VALUE; // the maximum possible Integer number that can be represented in 32 bits
        
        for (Professor professor : professors) {
            int currentLoad = professor.getSupervisedPositions().size();
            System.out.println("=========Professor:"+professor+"size:"+currentLoad);
            if (currentLoad<lowestLoad) {
                lowestLoad  = currentLoad;
                bestMatch = professor;
            }
        }

        if (bestMatch != null) {
            position.setSupervisor(bestMatch);
            traineeshipPositionRepository.save(position);
            
            bestMatch.getSupervisedPositions().add(position);
            professorRepository.save(bestMatch);           
        } else {
            throw new IllegalStateException("No suitable professor found based on load");
        }
    }
}
