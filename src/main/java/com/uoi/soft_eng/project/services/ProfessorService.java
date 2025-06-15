package com.uoi.soft_eng.project.services;

import java.util.List;

import com.uoi.soft_eng.project.model.Evaluation;
import com.uoi.soft_eng.project.model.Professor;
import com.uoi.soft_eng.project.model.TraineeshipPosition;

public interface ProfessorService {

    Professor retrieveProfile(String username);
    void saveProfile(Professor professor);
    List<TraineeshipPosition> retrieveAssignedPositions(String username);
    TraineeshipPosition getPositionById(Integer positionId);
    void saveEvaluation(Integer positionId, Evaluation evaluation);


}
