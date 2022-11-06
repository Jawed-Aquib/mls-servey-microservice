package com.marketlogic.surveyservice.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="survey")
@Data
public class SurveyEntity {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
     @Column(unique=true)
    private String surveyName;

    @Override
    public String toString() {
        return "SurveyEntity{" +
                "id=" + id +
                ", surveyName='" + surveyName + '\'' +
                '}';
    }
}
