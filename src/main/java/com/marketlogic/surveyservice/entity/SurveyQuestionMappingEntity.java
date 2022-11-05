package com.marketlogic.surveyservice.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="survey_question_mapping")
@Data
public class SurveyQuestionMappingEntity {

    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "survey_id")
    private SurveyEntity surveyEntity;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private SurveyQuestionEntity surveyQuestionEntity;

    @Override
    public String toString() {
        return "SurveyQuestionMappingEntity{" +
                "id=" + id +
                ", surveyEntity=" + surveyEntity.toString() +
                ", surveyQuestionEntity=" + surveyQuestionEntity.toString() +
                '}';
    }
}
