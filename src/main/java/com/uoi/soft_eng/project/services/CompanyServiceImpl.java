package com.uoi.soft_eng.project.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.uoi.soft_eng.project.model.Company;
import com.uoi.soft_eng.project.model.Evaluation;
import com.uoi.soft_eng.project.model.EvaluationType;
import com.uoi.soft_eng.project.model.TraineeshipPosition;
import com.uoi.soft_eng.project.repository.CompanyRepository;
import com.uoi.soft_eng.project.repository.EvaluationRepository;
import com.uoi.soft_eng.project.repository.TraineeshipPositionRepository;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepository companyRepository;
    
    @Autowired
    private TraineeshipPositionRepository traineeshipPositionRepository;
    
    @Autowired
    private EvaluationRepository evaluationRepository;

    @Override
    public Company retrieveProfile(String username) {
        // Retrieve company profile by username
        return companyRepository.findByUsername(username).orElse(null);
    }

    @Override
    public void saveProfile(Company company) {
        companyRepository.save(company);
    }

    @Override
    public List<TraineeshipPosition> retrieveAvailablePositions(String username) {
        return traineeshipPositionRepository.findAvailablePositionsByCompanyUsername(username);
    }

    @Override
    public void addPosition(String username, TraineeshipPosition position) {
        // Search for the student by username
        Optional<Company> companyOpt = companyRepository.findByUsername(username);
        
        if (companyOpt.isPresent()) {
            Company company = companyOpt.get();
            position.setCompany(company);  // Link the traineeship position to the found company
            traineeshipPositionRepository.save(position);  // Save the position
        } else {
            // Handle the case where the company is not found (optional)
            throw new RuntimeException("Company not found with name: " + username);
        }
    }

    @Override
    public void deletePosition(Integer positionId) {
    	traineeshipPositionRepository.deleteById(positionId);
    }

    @Override
    public List<TraineeshipPosition> retrieveAssignedPositions(String username) {
        // Retrieve the list of traineeship positions assigned to the company
        return traineeshipPositionRepository.findAssignedPositionsByCompanyUsername(username);
    }

    @Override
    public void saveEvaluation(Integer positionId, Evaluation evaluation) {
        // Retrieve the traineeship position by its ID
        Optional<TraineeshipPosition> positionOpt = traineeshipPositionRepository.findById(positionId);
    
        if (positionOpt.isPresent()) {
            TraineeshipPosition position = positionOpt.get();
    
            // Check if an existing evaluation with type 'COMPANY' already exists
            Evaluation existingEvaluation = position.getEvaluations().stream()
                    .filter(e -> EvaluationType.COMPANY.equals(e.getEvaluationType()))
                    .findFirst()
                    .orElse(new Evaluation(position, EvaluationType.COMPANY));
                
            existingEvaluation.setMotivation(evaluation.getMotivation());
            existingEvaluation.setEfficiency(evaluation.getEfficiency());
            existingEvaluation.setEffectiveness(evaluation.getEffectiveness());
                
            evaluationRepository.save(existingEvaluation);
            traineeshipPositionRepository.save(position);
        } else {        
            // Handle the case when the position isn't found
            throw new RuntimeException("Traineeship position not found with ID: " + positionId);
            
        }
    }

}
