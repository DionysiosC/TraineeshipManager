package com.uoi.soft_eng.project.model;


public enum EvaluationType {
    COMPANY("COMPANY"),
    PROFESSOR("PROFESSOR");
    
    private final String evaluationType;

    EvaluationType(String evaluationType) {
        this.evaluationType = evaluationType;
    }
    
    public String getValue() {
        return evaluationType;
    }
}