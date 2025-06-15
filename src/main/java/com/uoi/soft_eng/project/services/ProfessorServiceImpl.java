package com.uoi.soft_eng.project.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uoi.soft_eng.project.model.Evaluation;
import com.uoi.soft_eng.project.model.EvaluationType;
import com.uoi.soft_eng.project.model.Professor;
import com.uoi.soft_eng.project.model.TraineeshipPosition;
import com.uoi.soft_eng.project.repository.EvaluationRepository;
import com.uoi.soft_eng.project.repository.ProfessorRepository;
import com.uoi.soft_eng.project.repository.TraineeshipPositionRepository;

@Service
public class ProfessorServiceImpl implements ProfessorService {

    @Autowired
    private ProfessorRepository professorRepository;
    
    @Autowired
    private TraineeshipPositionRepository traineeshipPositionRepository;
    
    @Autowired
    private EvaluationRepository evaluationRepository;

    @Override
    public Professor retrieveProfile(String username) {
        // Retrieve professor profile by username
        return professorRepository.findByUsername(username)
                .orElse(null);  // Return null if the professor is not found
    }

    @Override
    public void saveProfile(Professor professor) {
        professorRepository.save(professor);
    }

    @Override
    public List<TraineeshipPosition> retrieveAssignedPositions(String username) {
        // Retrieve the list of traineeship positions assigned to the professor
        return traineeshipPositionRepository.findBySupervisorUsername(username);
    }

    @Override
    public TraineeshipPosition getPositionById(Integer positionId) {
        // Retrieve a traineeship position by its ID
        return traineeshipPositionRepository.findById(positionId).orElse(null); // Return null if not found
    }

    @Override
    public void saveEvaluation(Integer positionId, Evaluation evaluation) {
        // Retrieve the traineeship position by its ID
        Optional<TraineeshipPosition> positionOpt = traineeshipPositionRepository.findById(positionId);
    
        if (positionOpt.isPresent()) {
            TraineeshipPosition position = positionOpt.get();
    
            // Check if an existing evaluation with type 'PROFESSOR' already exists
            Evaluation existingEvaluation = position.getEvaluations().stream()
                    .filter(e -> EvaluationType.PROFESSOR.equals(e.getEvaluationType()))
                    .findFirst()
                    .orElse(new Evaluation(position, EvaluationType.PROFESSOR));
                
            existingEvaluation.setMotivation(evaluation.getMotivation());
            existingEvaluation.setEfficiency(evaluation.getEfficiency());
            existingEvaluation.setEffectiveness(evaluation.getEffectiveness());
            existingEvaluation.setCompanyScore(evaluation.getCompanyScore());
                
            evaluationRepository.save(existingEvaluation);
            traineeshipPositionRepository.save(position);
        } else {        
            // Handle the case when the position isn't found
            throw new RuntimeException("Traineeship position not found with ID: " + positionId);
            
        }
    }
}
