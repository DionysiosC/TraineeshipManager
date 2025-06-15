package com.uoi.soft_eng.project.strategy.assignments;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.uoi.soft_eng.project.model.Professor;
import com.uoi.soft_eng.project.model.TraineeshipPosition;
import com.uoi.soft_eng.project.repository.ProfessorRepository;
import com.uoi.soft_eng.project.repository.TraineeshipPositionRepository;

@Component
public class AssignmentBasedOnInterests implements SupervisorAssignmentStrategy{
    
    private static final double THRESHOLD = 0.5;
    
    @Autowired
    private ProfessorRepository professorRepository; 
    
    @Autowired
    private TraineeshipPositionRepository traineeshipPositionRepository;

    @Override
    public void assign(Integer positionID){
        TraineeshipPosition position = traineeshipPositionRepository.findById(positionID).orElse(null);
        List<Professor> professors = professorRepository.findAll();
        if (position.getTopics()==null)
            return;
        List<String> positionTopics = cleanData(position.getTopics());
        Professor bestMatch = null;
        double bestSimilarity = 0.0;

        for (Professor professor : professors) {
            if (professor.getInterests()==null)
                continue;
            List<String> professorInterests = cleanData(professor.getInterests());

            Set<String> intersection = new HashSet<>(professorInterests);
            intersection.retainAll(positionTopics);

            Set<String> union = new HashSet<>(professorInterests);
            union.addAll(positionTopics);

            double similarity = union.isEmpty() ? 0.0 : (double) intersection.size() / union.size();

            if (similarity >= THRESHOLD && similarity > bestSimilarity) {
                bestSimilarity = similarity;
                bestMatch = professor;
            }
        }

        if (bestMatch != null) {
            position.setSupervisor(bestMatch);
            traineeshipPositionRepository.save(position);
            
            bestMatch.getSupervisedPositions().add(position);
            professorRepository.save(bestMatch);
        } else {
            throw new IllegalStateException("No suitable professor found based on interests");
        }
    }
    
    
    private List<String> cleanData(String input) {
        return Arrays.stream(input.split(","))
                                            .filter(Objects::nonNull)                   // remove nulls
                                            .map(String::trim)                          // trim whitespace
                                            .filter(s -> !s.isEmpty())                  // remove empty strings
                                            .map(String::toLowerCase)                   // to lower case
                                            .distinct()                                 // remove duplicates (optional)
                                            .collect(Collectors.toList());
    }    
}
