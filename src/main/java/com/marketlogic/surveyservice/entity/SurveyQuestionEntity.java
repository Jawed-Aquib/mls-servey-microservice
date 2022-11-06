package com.marketlogic.surveyservice.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "survey_question")
public class SurveyQuestionEntity {

    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    private String question;
    @Column(name = "is_active")
    private Boolean isActive;

    @Override
    public String toString() {
        return "SurveyQuestionEntity{" +
                "id=" + id +
                ", question='" + question + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
