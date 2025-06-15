package com.uoi.soft_eng.project.strategy.assignments;

import org.springframework.stereotype.Component;

@Component
public interface SupervisorAssignmentStrategy{
    void assign(Integer positionId);
}
