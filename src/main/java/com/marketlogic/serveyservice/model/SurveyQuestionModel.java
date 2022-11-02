package com.marketlogic.serveyservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SurveyQuestionModel {
    private Integer id;
    private String question;
    private List<SurveyAnswerModel> surveyAnswerBeans;

}
