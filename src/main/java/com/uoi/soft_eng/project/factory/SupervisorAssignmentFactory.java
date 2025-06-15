package com.uoi.soft_eng.project.factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.uoi.soft_eng.project.strategy.assignments.AssignmentBasedOnInterests;
import com.uoi.soft_eng.project.strategy.assignments.AssignmentBasedOnLoad;
import com.uoi.soft_eng.project.strategy.assignments.SupervisorAssignmentStrategy;

@Component
public class SupervisorAssignmentFactory {
    @Autowired
    private AssignmentBasedOnLoad assignmentBasedOnLoad;
    
    @Autowired
    private AssignmentBasedOnInterests assignmentBasedOnInterests;
    
    public SupervisorAssignmentStrategy create(String strategy) {
        SupervisorAssignmentStrategy searchStrategy;

        switch (strategy) {
            case "load":
                searchStrategy = assignmentBasedOnLoad;
                break;
            case "interests":
                searchStrategy = assignmentBasedOnInterests;
                break;
            default:
                throw new IllegalArgumentException("Invalid search strategy");
        }
        return searchStrategy;
    }
}
