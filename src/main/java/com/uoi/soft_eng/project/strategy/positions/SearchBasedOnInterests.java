package com.uoi.soft_eng.project.strategy.positions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.uoi.soft_eng.project.model.Student;
import com.uoi.soft_eng.project.model.TraineeshipPosition;
import com.uoi.soft_eng.project.repository.TraineeshipPositionRepository;
import com.uoi.soft_eng.project.services.StudentService;

@Component
public class SearchBasedOnInterests implements PositionsSearchStrategy{
    
    private static final double THRESHOLD = 0.5;
    
    @Autowired
    private StudentService studentService; 
    
    @Autowired
    private TraineeshipPositionRepository traineeshipPositionRepository;

    @Override
    public List <TraineeshipPosition> search(String applicantUsername){    
        Student student = studentService.retrieveProfile(applicantUsername);
        if (student.getInterests()==null || student.getSkills()==null)
            return null; 
        List<String> studentInterests = cleanData(student.getInterests());
        List<String> studentSkills = cleanData(student.getSkills());
        List<TraineeshipPosition> positions = traineeshipPositionRepository.returnAllAvailablePositions();
        List<TraineeshipPosition> matchedPositions = new ArrayList<>();
        
        for (TraineeshipPosition position : positions) {
            List<String> positionTopics = cleanData(position.getTopics());
            List<String> positionSkills = cleanData(position.getSkills());
            Set<String> intersection = new HashSet<>(studentInterests);
            intersection.retainAll(positionTopics);
            
            Set<String> union = new HashSet<>(studentInterests);
            union.addAll(positionTopics);
    
            double similarity = (double) intersection.size() / union.size();
        
            boolean skillsMatch = positionSkills.containsAll(studentSkills);    
            if (similarity >= THRESHOLD && skillsMatch) {
                matchedPositions.add(position);
            }
        }
    
        return matchedPositions;       
    }
    
    private List<String> cleanData(String input) {
        return Arrays.stream(input.split(","))
                                        .filter(Objects::nonNull)   // remove nulls
                                        .map(String::trim)          // trim whitespace
                                        .filter(s -> !s.isEmpty())  // remove empty strings
                                        .map(String::toLowerCase)   // to lower case
                                        .distinct()                 // remove duplicates (optional)
                                        .collect(Collectors.toList());
    }
}
