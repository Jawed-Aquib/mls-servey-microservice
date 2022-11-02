package com.marketlogic.serveyservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "survey_answer")
public class SurveyAnswerEntity {
    @Id
    private Integer id;

    private String value;

    @ManyToOne()
    @JoinColumn(name = "survey_question_id")
    @JsonIgnore
    private SurveyQuestionEntity surveyQuestionEntity;
}
