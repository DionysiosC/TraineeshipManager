package com.uoi.soft_eng.project.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@Entity
@ToString
@Table(name = "Evaluation")
public class Evaluation {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(name = "evaluation_type")
    private EvaluationType evaluationType;
    
    @Column(name = "motivation")
    private Integer motivation;

    @Column(name = "efficiency")
    private Integer efficiency;

    @Column(name = "effectiveness")
    private Integer effectiveness;
    
    @Column(name = "company_score")
    private Integer companyScore;    

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "traineeship_id", referencedColumnName = "id") // Map the foreign key column
    private TraineeshipPosition traineeshipPosition;
    
    public Evaluation(TraineeshipPosition traineeshipPosition, EvaluationType evaluationType){
        this.traineeshipPosition=traineeshipPosition;
        this.evaluationType=evaluationType;
    }
    
}