package com.uoi.soft_eng.project.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.uoi.soft_eng.project.model.TraineeshipPosition;

@Repository
public interface TraineeshipPositionRepository extends JpaRepository<TraineeshipPosition, Integer> {

    @Query(value = "SELECT tp.* "+
                    "FROM Traineeship_position tp "+
                    "JOIN User u ON tp.company_id = u.id "+
                    "WHERE u.username=?1 "+ 
                    "AND tp.is_Assigned = FALSE", nativeQuery = true)
    List<TraineeshipPosition> findAvailablePositionsByCompanyUsername(String companyUsername);

    @Query(value = "SELECT tp.* "+
                    "FROM Traineeship_position tp "+
                    "JOIN User u ON tp.company_id = u.id "+
                    "WHERE u.username=?1 "+ 
                    "AND tp.is_Assigned = TRUE "+
                    "AND tp.pass_fail_grade = FALSE", nativeQuery = true)
    List<TraineeshipPosition> findAssignedPositionsByCompanyUsername(String companyUsername);

    // Retrieve a traineeship position by its ID
    @Override
    Optional<TraineeshipPosition> findById(Integer id);
    
    @Query(value = "SELECT tp.* "+
                    "FROM Traineeship_position tp "+
                    "JOIN User u ON tp.supervisor_id = u.id "+
                    "WHERE u.username=?1 "+ 
                    "AND tp.is_Assigned = TRUE", nativeQuery = true)
    List<TraineeshipPosition> findBySupervisorUsername(String supervisorUsername);
    
    @Query(value = "SELECT tp.* "+
                    "FROM Traineeship_position tp "+ 
                    "WHERE tp.is_Assigned = TRUE "+
                    "AND tp.pass_fail_grade = FALSE", nativeQuery = true)
    List<TraineeshipPosition> returnAllAssignedPositions();
    
    @Query(value = "SELECT tp.* "+
                    "FROM Traineeship_position tp "+ 
                    "WHERE tp.is_Assigned = FALSE", nativeQuery = true)
    List<TraineeshipPosition> returnAllAvailablePositions();
}
