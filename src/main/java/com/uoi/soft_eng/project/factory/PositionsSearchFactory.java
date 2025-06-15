package com.uoi.soft_eng.project.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.uoi.soft_eng.project.strategy.positions.PositionsSearchStrategy;
import com.uoi.soft_eng.project.strategy.positions.SearchBasedOnInterests;
import com.uoi.soft_eng.project.strategy.positions.SearchBasedOnLocation;
import com.uoi.soft_eng.project.strategy.positions.SearchBasedOnBoth;

@Component
public class PositionsSearchFactory {

    @Autowired
    private SearchBasedOnLocation searchBasedOnLocation;
    
    @Autowired
    private SearchBasedOnInterests searchBasedOnInterests;
    
    @Autowired
    private SearchBasedOnBoth searchBasedOnBoth;    
    
    public PositionsSearchStrategy create(String strategy) {
        PositionsSearchStrategy searchStrategy;

        switch (strategy) {
            case "location":
                searchStrategy = searchBasedOnLocation;
                break;
            case "interests":
                searchStrategy = searchBasedOnInterests;
                break;
            case "both":
                return searchStrategy = searchBasedOnBoth;
            default:
                throw new IllegalArgumentException("Invalid search strategy");
        }

        return searchStrategy;
    }   
    
}
