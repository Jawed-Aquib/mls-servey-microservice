package com.marketlogic.surveyservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "survey_option")
public class SurveyOptionEntity {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    private String value;

    @ManyToOne()
    @JoinColumn(name = "survey_question_id")
    @JsonIgnore
    private SurveyQuestionEntity surveyQuestionEntity;

    @Override
    public String toString() {
        return "SurveyAnswerEntity{" +
                "id=" + id +
                ", value='" + value + '\'' +
                ", surveyQuestionEntity=" + surveyQuestionEntity.toString() +
                '}';
    }
}
