package com.uoi.soft_eng.project.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.uoi.soft_eng.project.model.Student;
import com.uoi.soft_eng.project.model.User;


@Repository
public interface StudentRepository extends JpaRepository<User, String> {
    Optional<Student> findByUsername(String username);
    
    @Query(value = "SELECT * "+
                    "FROM user u "+
                    "WHERE u.looking_for_traineeship=true", nativeQuery = true)
    List<Student> retrieveTraineeshipApplications();
}