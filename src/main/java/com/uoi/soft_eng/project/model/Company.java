package com.uoi.soft_eng.project.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
public class Company extends User {

    public Company(String username, String password, Role role){
        super.username = username;
        super.password = password;
        super.role=role;
    }

    @Column(name = "fullName")
    private String companyName;

    @Column(name = "companyLocation")
    private String companyLocation;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<TraineeshipPosition> positions;
}
