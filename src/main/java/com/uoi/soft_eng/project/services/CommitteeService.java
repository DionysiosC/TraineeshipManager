package com.uoi.soft_eng.project.services;

import java.util.List;

import com.uoi.soft_eng.project.model.Student;
import com.uoi.soft_eng.project.model.TraineeshipPosition;

public interface CommitteeService {

    List<TraineeshipPosition> retrievePositionsForApplicant(String applicantUsername, String Strategy);

    List<Student> retrieveTraineeshipApplications();

    void assignPosition(Integer positionId, String studentUsername);

    void assignSupervisor(Integer positionId, String strategy);

    List<TraineeshipPosition> listAssignedTraineeships();

    void completeAssignedTraineeships(Integer positionId, Double finalScore);
}