package com.uoi.soft_eng.project.strategy.positions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.uoi.soft_eng.project.model.Student;
import com.uoi.soft_eng.project.model.TraineeshipPosition;
import com.uoi.soft_eng.project.repository.TraineeshipPositionRepository;
import com.uoi.soft_eng.project.services.StudentService;

@Component
public class SearchBasedOnLocation implements PositionsSearchStrategy{

    @Autowired
    private StudentService studentService; 
    
    @Autowired
    private TraineeshipPositionRepository traineeshipPositionRepository;

    @Override
    public List <TraineeshipPosition> search(String applicantUsername){
        Student student = studentService.retrieveProfile(applicantUsername);
        if (student.getSkills()==null || student.getPreferredLocation() == null)
            return null; 
        List<TraineeshipPosition> positions = traineeshipPositionRepository.returnAllAvailablePositions();
        List<String> studentSkills = cleanData(student.getSkills());
                
        String targetLocation = student.getPreferredLocation().toLowerCase();
        
        String positionLocation;
        List<TraineeshipPosition> matchedPositions = new ArrayList<>();
        for (TraineeshipPosition position : positions) {
            List<String> positionSkills = cleanData(position.getSkills());
            
            if (position.getCompany().getCompanyLocation() == null)
                continue;
            positionLocation = position.getCompany().getCompanyLocation().toLowerCase();
            
            boolean skillsMatch = positionSkills.containsAll(studentSkills);
            boolean locationMatches = targetLocation == null ? positionLocation == null : targetLocation.equals(positionLocation);
            if (locationMatches && skillsMatch){
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
