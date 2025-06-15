package com.uoi.soft_eng.project.services;

import java.util.List;

import com.uoi.soft_eng.project.model.Company;
import com.uoi.soft_eng.project.model.Evaluation;
import com.uoi.soft_eng.project.model.TraineeshipPosition;

public interface CompanyService {

    Company retrieveProfile(String username);
    void saveProfile(Company company);
    List<TraineeshipPosition> retrieveAvailablePositions(String username);
    void addPosition(String username, TraineeshipPosition position);
    void deletePosition(Integer positionId);
    List<TraineeshipPosition> retrieveAssignedPositions(String username);
    void saveEvaluation(Integer positionId, Evaluation evaluation);

}
