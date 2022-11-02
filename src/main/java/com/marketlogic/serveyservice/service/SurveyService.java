package com.marketlogic.serveyservice.service;

import com.marketlogic.serveyservice.model.SurveyQuestionModel;
import com.marketlogic.serveyservice.model.SurveyQuestionStatusModel;

import java.util.List;

public interface SurveyService {
    public List<SurveyQuestionModel> getSurveyQuestion();
    public List<SurveyQuestionModel> updateSurveyQuestions(List<SurveyQuestionStatusModel> surveyQuestionStatusModelList);
}
