package com.uoi.soft_eng.project.strategy.positions;
import java.util.List;

import org.springframework.stereotype.Component;

import com.uoi.soft_eng.project.model.TraineeshipPosition;

@Component
public interface PositionsSearchStrategy{
    List <TraineeshipPosition> search(String applicantUsername);
}
