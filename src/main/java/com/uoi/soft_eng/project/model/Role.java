package com.uoi.soft_eng.project.model;

public enum Role {
    COMMITTEE("COMMITTEE"),
    STUDENT("STUDENT"),
    COMPANY("COMPANY"),
    PROFESSOR("PROFESSOR");

    private final String role;

    Role(String role) {
        this.role = role;
    }
    
    public String getValue() {
        return role;
    }
    
}

