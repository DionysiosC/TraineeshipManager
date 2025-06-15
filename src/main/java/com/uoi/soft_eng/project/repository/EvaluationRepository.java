package com.uoi.soft_eng.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uoi.soft_eng.project.model.Evaluation;

@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, String> {

}
