package com.marketlogic.surveyservice.service;

import com.marketlogic.surveyservice.model.SurveyQuestionModel;
import com.marketlogic.surveyservice.model.SurveyQuestionStatusModel;

import java.util.List;

public interface SurveyQuestionService {
    public List<SurveyQuestionModel> getSurveyQuestion();
    public List<SurveyQuestionModel> updateSurveyQuestions(List<SurveyQuestionStatusModel> surveyQuestionStatusModelList);
    public SurveyQuestionModel createSurveyQuestionModel(SurveyQuestionModel surveyQuestionModel);
}
